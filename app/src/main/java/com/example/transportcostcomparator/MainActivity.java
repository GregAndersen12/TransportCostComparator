package com.example.transportcostcomparator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartCalc = findViewById(R.id.btnStartCalc);
        Button btnListenToMe = findViewById(R.id.btnListenToMe);
        Button btnExit = findViewById(R.id.btnExit);

        // Top nav bar views
        TextView navHome = findViewById(R.id.navHome);
        TextView navDetails = findViewById(R.id.navDetails);
        TextView navReport = findViewById(R.id.navReport);
        ImageView navHelp = findViewById(R.id.navHelp);

        // 1. Navigation to Input Screen
        btnStartCalc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            startActivity(intent);
        });

        // 2. Listen to Me Audio Button
        btnListenToMe.setOnClickListener(v -> {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.transport_audio);
            }
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                Toast.makeText(this, "Playing Minister of Transport Speech...", Toast.LENGTH_SHORT).show();
            } else {
                mediaPlayer.pause();
            }
        });

        // 3. Exit Application
        btnExit.setOnClickListener(v -> {
            finishAffinity();
        });

        // 4. Top Nav Bar Navigation
        navHome.setOnClickListener(v ->
                Toast.makeText(this, "Already on Home Screen", Toast.LENGTH_SHORT).show());

        navDetails.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, DetailsActivity.class)));

        navReport.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ReportsActivity.class)));

        navHelp.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HelpActivity.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}