package com.example.transportcostcomparator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

        // a. Retrieve from Database
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
        // Assume dbHelper.getReport(id) returns a populated Transport object
        Transport report = dbHelper.getReport(id);

        if (report != null) {
            double monthlyCost = report.getMonthlyCost();

            // a. Display Calculation Results
            tvResMode.setText("Mode: " + report.getModeOfTransport() + " (" + report.getTransportType() + ")");
            tvResDailyCost.setText(String.format("Daily Transport Cost: R%.2f", report.getDailyCost()));
            tvResMonthlyCost.setText(String.format("Monthly Transport Cost: R%.2f", monthlyCost));
            tvResMonthlyDistance.setText(String.format("Monthly Distance: %.1f km", report.getTotalMonthlyDistance()));

            // Execute conditional methods
            String category = determineCostCategory(monthlyCost);
            String recommendation = determineRecommendation(category);

            // b & c. Display rating and recommendation
            tvResCategory.setText("Cost Category: " + category);
            tvResRecommendation.setText("Recommendation: " + recommendation);
        }
    }

    // b. Determine Transport Cost Category (Energy Efficiency Rating Table)
    private String determineCostCategory(double monthlyCost) {
        if (monthlyCost < 1000.0) {
            return "Low Cost";
        } else if (monthlyCost >= 1000.0 && monthlyCost < 2000.0) { // R1000 - R1999
            return "Moderate";
        } else if (monthlyCost >= 2000.0 && monthlyCost < 3000.0) { // R2000 - R2999
            return "High";
        } else if (monthlyCost >= 3000.0 && monthlyCost < 5000.0) { // R3000 - R4999
            return "Very High";
        } else {
            return "Excessive"; // R5000 and above
        }
    }

    // c. Display Transport Cost Recommendation
    private String determineRecommendation(String category) {
        switch (category) {
            case "Low Cost":
                return "Continue using your current transport method.";
            case "Moderate":
                return "Consider carpooling where possible.";
            case "High":
                return "Reduce unnecessary trips and combine errands.";
            case "Very High":
                return "Consider public transport for regular commuting.";
            case "Excessive":
                return "Immediate action is recommended to reduce transport costs.";
            default:
                return "No recommendation available.";
        }
    }
}