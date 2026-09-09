package com.example.transportcostcomparator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class InputActivity extends AppCompatActivity {

    private EditText etModeOfTransport, etDistance, etCostPerKm;
    private Spinner spTransportType;
    private DatePicker datePickerTravel;
    private Button btnCalculateSave, btnClear;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        dbHelper = new DatabaseHelper(this);

        etModeOfTransport = findViewById(R.id.etModeOfTransport);
        etDistance = findViewById(R.id.etDistance);
        etCostPerKm = findViewById(R.id.etCostPerKm);
        spTransportType = findViewById(R.id.spTransportType);
        datePickerTravel = findViewById(R.id.datePickerTravel);
        btnCalculateSave = findViewById(R.id.btnCalculateSave);
        btnClear = findViewById(R.id.btnClear);

        // Spinner adapter (Step 1)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.transport_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransportType.setAdapter(adapter);

        // Top nav bar
        TextView navHome = findViewById(R.id.navHome);
        TextView navDetails = findViewById(R.id.navDetails);
        TextView navReport = findViewById(R.id.navReport);
        ImageView navHelp = findViewById(R.id.navHelp);

        navHome.setOnClickListener(v -> {
            startActivity(new Intent(InputActivity.this, MainActivity.class));
            finish();
        });
        navDetails.setOnClickListener(v ->
                startActivity(new Intent(InputActivity.this, DetailsActivity.class)));
        navReport.setOnClickListener(v ->
                startActivity(new Intent(InputActivity.this, ReportsActivity.class)));
        navHelp.setOnClickListener(v ->
                startActivity(new Intent(InputActivity.this, HelpActivity.class)));

        btnCalculateSave.setOnClickListener(v -> handleCalculation());

        btnClear.setOnClickListener(v -> {
            etModeOfTransport.setText("");
            etDistance.setText("");
            etCostPerKm.setText("");
        });
    }

    private void handleCalculation() {
        String mode = etModeOfTransport.getText().toString().trim();
        String distanceStr = etDistance.getText().toString().trim();
        String costStr = etCostPerKm.getText().toString().trim();
        String type = spTransportType.getSelectedItem().toString();

        if (mode.isEmpty() || distanceStr.isEmpty() || costStr.isEmpty()) {
            Toast.makeText(this, "Error: All fields must be filled.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            double distancePerDay = Double.parseDouble(distanceStr);
            double costPerKm = Double.parseDouble(costStr);

            Calendar today = Calendar.getInstance();
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(datePickerTravel.getYear(), datePickerTravel.getMonth(), datePickerTravel.getDayOfMonth());

            long diffInMillis = selectedDate.getTimeInMillis() - today.getTimeInMillis();
            int travelDays = (int) TimeUnit.MILLISECONDS.toDays(diffInMillis);

            if (travelDays <= 0) {
                Toast.makeText(this, "Error: Travel date must be in the future.", Toast.LENGTH_LONG).show();
                return;
            }

            // Step 10 — using TransportCalculator instead of inline math
            double dailyCost = TransportCalculator.calculateDailyCost(distancePerDay, costPerKm);
            double monthlyCost = TransportCalculator.calculateMonthlyCost(dailyCost, travelDays);
            double monthlyDistance = TransportCalculator.calculateMonthlyDistance(distancePerDay, travelDays);

            long reportId = dbHelper.insertReport(mode, type, distancePerDay, costPerKm, travelDays, dailyCost, monthlyCost, monthlyDistance);

            if (reportId != -1) {
                Toast.makeText(this, "Report Saved Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InputActivity.this, ResultsActivity.class);
                intent.putExtra("REPORT_ID", reportId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Database Error: Could not save report.", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error: Please enter valid numeric values for Distance and Cost.", Toast.LENGTH_LONG).show();
        }
    }
}