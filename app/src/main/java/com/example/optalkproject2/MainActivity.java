package com.example.optalkproject2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.optalkproject2.databinding.ActivityMainBinding;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up button click listeners
        Button startButton = findViewById(R.id.startButton);
        Button settingsButton = findViewById(R.id.settingsButton);
        Button quitButton = findViewById(R.id.quitButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the FormActivity when Start button is clicked
                startActivity(new Intent(MainActivity.this, FormActivity.class));
            }
        });

        // Implement actions for other buttons if needed

    }
}