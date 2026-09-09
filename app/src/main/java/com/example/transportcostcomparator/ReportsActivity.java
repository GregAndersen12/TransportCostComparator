package com.example.transportcostcomparator;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
                android.R.layout.simple_list_item_1, lines);
        lvReports.setAdapter(adapter);
    }
}