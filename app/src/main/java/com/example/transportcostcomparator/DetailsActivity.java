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

public class DetailsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dbHelper = new DatabaseHelper(this);
        ListView lvDetails = findViewById(R.id.lvDetails);

        // Top nav bar
        TextView navHome = findViewById(R.id.navHome);
        TextView navDetails = findViewById(R.id.navDetails);
        TextView navReport = findViewById(R.id.navReport);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(DetailsActivity.this, MainActivity.class));
            finish();
        });
        navDetails.setOnClickListener(v ->
                Toast.makeText(this, "Already on Details Screen", Toast.LENGTH_SHORT).show());
        navReport.setOnClickListener(v ->
                startActivity(new Intent(DetailsActivity.this, ReportsActivity.class)));

        List<Transport> reports = dbHelper.getAllReports();
        List<String> lines = new ArrayList<>();

        for (Transport t : reports) {
            lines.add(t.getDateCreated() + "  |  " + t.getModeOfTransport()
                    + " (" + t.getTransportType() + ")\n"
                    + "Daily: R" + String.format("%.2f", t.getDailyCost())
                    + "   Monthly: R" + String.format("%.2f", t.getMonthlyCost())
                    + "   Distance: " + String.format("%.1f", t.getTotalMonthlyDistance()) + " km");
        }

        if (lines.isEmpty()) {
            lines.add("No saved reports yet. Complete a calculation to see it here.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.list_item_white, android.R.id.text1, lines);
        lvDetails.setAdapter(adapter);
    }
}