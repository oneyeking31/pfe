package com.example.exam_gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Opnnenig extends AppCompatActivity {
    Timer timer;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opnnenig_activity);

        progressBar = (ProgressBar)findViewById(R.id.spin_kit);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent=new Intent(Opnnenig.this,Login.class);
                startActivity(intent);
                finish();
            }
        },4000);

    }

    }

