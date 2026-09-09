package com.example.transportcostcomparator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private TextView tvResMode, tvResDailyCost, tvResMonthlyCost, tvResMonthlyDistance;
    private TextView tvResCategory, tvResRecommendation;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        dbHelper = new DatabaseHelper(this);

        tvResMode = findViewById(R.id.tvResModeOfTransport);
        tvResDailyCost = findViewById(R.id.tvResDailyCost);
        tvResMonthlyCost = findViewById(R.id.tvResMonthlyCost);
        tvResMonthlyDistance = findViewById(R.id.tvResMonthlyDistance);
        tvResCategory = findViewById(R.id.tvResCategory);
        tvResRecommendation = findViewById(R.id.tvResRecommendation);

        Button btnCalcAgain = findViewById(R.id.btnCalcAgain);
        Button btnResultsExit = findViewById(R.id.btnResultsExit);

        // Top nav bar
        TextView navHome = findViewById(R.id.navHome);
        TextView navDetails = findViewById(R.id.navDetails);
        TextView navReport = findViewById(R.id.navReport);
        ImageView navHelp = findViewById(R.id.navHelp);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(ResultsActivity.this, MainActivity.class));
            finish();
        });
        navDetails.setOnClickListener(v ->
                startActivity(new Intent(ResultsActivity.this, DetailsActivity.class)));
        navReport.setOnClickListener(v ->
                startActivity(new Intent(ResultsActivity.this, ReportsActivity.class)));
        navHelp.setOnClickListener(v ->
                startActivity(new Intent(ResultsActivity.this, HelpActivity.class)));

        long reportId = getIntent().getLongExtra("REPORT_ID", -1);
        if (reportId != -1) {
            loadReportFromDatabase(reportId);
        } else {
            Toast.makeText(this, "Error loading report data.", Toast.LENGTH_SHORT).show();
        }

        btnCalcAgain.setOnClickListener(v -> finish());
        btnResultsExit.setOnClickListener(v -> finishAffinity());
    }

    private void loadReportFromDatabase(long id) {
        Transport report = dbHelper.getReport(id);

        if (report != null) {
            double monthlyCost = report.getMonthlyCost();

            tvResMode.setText("Mode: " + report.getModeOfTransport() + " (" + report.getTransportType() + ")");
            tvResDailyCost.setText(String.format("Daily Transport Cost: R%.2f", report.getDailyCost()));
            tvResMonthlyCost.setText(String.format("Monthly Transport Cost: R%.2f", monthlyCost));
            tvResMonthlyDistance.setText(String.format("Monthly Distance: %.1f km", report.getTotalMonthlyDistance()));

            // Step 9 — using Recommendation class instead of inline private methods
            String category = Recommendation.getCostCategory(monthlyCost);
            String recommendation = Recommendation.getRecommendation(category);

            tvResCategory.setText("Cost Category: " + category);
            tvResRecommendation.setText("Recommendation: " + recommendation);
        }
    }
}