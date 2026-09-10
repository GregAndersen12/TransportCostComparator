package com.example.transportcostcomparator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dbHelper = new DatabaseHelper(this);
        TextView tvSummary = findViewById(R.id.tvReportsSummary);
        ListView lvReports = findViewById(R.id.lvReports);

        // Top nav bar
        TextView navHome = findViewById(R.id.navHome);
        TextView navDetails = findViewById(R.id.navDetails);
        TextView navReport = findViewById(R.id.navReport);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(ReportsActivity.this, MainActivity.class));
            finish();
        });
        navDetails.setOnClickListener(v ->
                startActivity(new Intent(ReportsActivity.this, DetailsActivity.class)));
        navReport.setOnClickListener(v ->
                Toast.makeText(this, "Already on Report Screen", Toast.LENGTH_SHORT).show());

        List<Transport> reports = dbHelper.getAllReports();
        List<String> lines = new ArrayList<>();
        double totalMonthlyCost = 0;

        for (Transport t : reports) {
            totalMonthlyCost += t.getMonthlyCost();
            lines.add(t.getDateCreated() + "  |  " + t.getModeOfTransport()
                    + "  |  R" + String.format("%.2f", t.getMonthlyCost()) + "/month");
        }

        if (reports.isEmpty()) {
            tvSummary.setText("No reports saved yet.");
        } else {
            double avg = totalMonthlyCost / reports.size();
            tvSummary.setText(String.format("%d reports saved  |  Average monthly cost: R%.2f",
                    reports.size(), avg));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.list_item_white, android.R.id.text1, lines);
        lvReports.setAdapter(adapter);
    }
}