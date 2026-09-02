package com.example.transportcostcomparator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class InputActivity extends AppCompatActivity {

    private EditText etModeOfTransport, etDistance, etCostPerKm;
    private Spinner spTransportType;
    private DatePicker datePickerTravel;
    private Button btnCalculateSave, btnClear;
    private DatabaseHelper dbHelper; // Assumes you have created this class

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

        // a. Input Validation (Checking for empty fields)
        if (mode.isEmpty() || distanceStr.isEmpty() || costStr.isEmpty()) {
            Toast.makeText(this, "Error: All fields must be filled.", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            // a. Ensure variables contain numeric values
            double distancePerDay = Double.parseDouble(distanceStr);
            double costPerKm = Double.parseDouble(costStr);

            // Determine travel days using DatePicker (Days between today and selected date)
            Calendar today = Calendar.getInstance();
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(datePickerTravel.getYear(), datePickerTravel.getMonth(), datePickerTravel.getDayOfMonth());

            long diffInMillis = selectedDate.getTimeInMillis() - today.getTimeInMillis();
            int travelDays = (int) TimeUnit.MILLISECONDS.toDays(diffInMillis);

            if (travelDays <= 0) {
                Toast.makeText(this, "Error: Travel date must be in the future.", Toast.LENGTH_LONG).show();
                return;
            }

            // b. Transport Calculations
            double dailyCost = distancePerDay * costPerKm;
            double monthlyCost = dailyCost * travelDays;
            double monthlyDistance = distancePerDay * travelDays;

            // Save to Database (Assuming dbHelper.insertReport returns the new row ID)
            long reportId = dbHelper.insertReport(mode, type, distancePerDay, costPerKm, travelDays, dailyCost, monthlyCost, monthlyDistance);

            if (reportId != -1) {
                Toast.makeText(this, "Report Saved Successfully!", Toast.LENGTH_SHORT).show();
                // Pass the ID to Results screen to fulfill Q10's "Retrieve from Database" requirement
                Intent intent = new Intent(InputActivity.this, ResultsActivity.class);
                intent.putExtra("REPORT_ID", reportId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Database Error: Could not save report.", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            // a. Display suitable error message for invalid data
            Toast.makeText(this, "Error: Please enter valid numeric values for Distance and Cost.", Toast.LENGTH_LONG).show();
        }
    }
}