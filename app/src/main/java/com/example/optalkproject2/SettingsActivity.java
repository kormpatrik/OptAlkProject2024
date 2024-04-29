package com.example.optalkproject2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";
    private Spinner languageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Initialize views
        languageSpinner = findViewById(R.id.languageSpinner);

        // Set up language spinner
        setUpLanguageSpinner();

        // Retrieve and set saved language preference
        setSavedLanguage();
    }

    // Set up language spinner with adapter and item selection listener
    private void setUpLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.language_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLanguage = parent.getItemAtPosition(position).toString();
                setAppLanguage(selectedLanguage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Retrieve saved language preference and set the spinner accordingly
    private void setSavedLanguage() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedLanguage = preferences.getString("language", "");
        if (!savedLanguage.isEmpty()) {
            int index = getResources().getStringArray(R.array.language_options).length;
            for (int i = 0; i < index; i++) {
                if (getResources().getStringArray(R.array.language_options)[i].equals(savedLanguage)) {
                    languageSpinner.setSelection(i);
                    break;
                }
            }
        }
    }

    // Change app language based on selected language
    private void setAppLanguage(String language) {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedLanguage = preferences.getString("language", "");

        if (!language.equals(savedLanguage)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("language", language);
            editor.apply();

            setLocale(language);

            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    // Set locale to apply language changes
// Set locale to apply language changes
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);

        Context context = createConfigurationContext(configuration);
        Resources resources = context.getResources();

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        Log.d(TAG, "Locale set to: " + languageCode);
    }

}
