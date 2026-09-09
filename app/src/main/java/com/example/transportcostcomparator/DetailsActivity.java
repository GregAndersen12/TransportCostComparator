package com.example.transportcostcomparator;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
                android.R.layout.simple_list_item_1, lines);
        lvDetails.setAdapter(adapter);
    }
}