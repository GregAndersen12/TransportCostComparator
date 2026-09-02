package com.example.transportcostcomparator;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartCalc = findViewById(R.id.btnStartCalc);
        TextView btnListenToMe = findViewById(R.id.btnListenToMe);
        Button btnExit = findViewById(R.id.btnExit);

        // 1. Navigation to Input Screen
        btnStartCalc.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            startActivity(intent);
        });

        // 2. Listen to Me Audio Button
        btnListenToMe.setOnClickListener(v -> {
            if (mediaPlayer == null) {
                // Requires an audio file in res/raw/transport_audio.mp3
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
            finishAffinity(); // Closes all activities and exits
        });
    }

    // 4. Action Bar Navigation Setup
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "Already on Home Screen", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.nav_details) {
            // Intent intent = new Intent(this, DetailsActivity.class);
            // startActivity(intent);
            return true;
        } else if (id == R.id.nav_reports) {
            // Intent intent = new Intent(this, ReportsActivity.class);
            // startActivity(intent);
            return true;
        } else if (id == R.id.nav_help) {
            // Intent intent = new Intent(this, HelpActivity.class);
            // startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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