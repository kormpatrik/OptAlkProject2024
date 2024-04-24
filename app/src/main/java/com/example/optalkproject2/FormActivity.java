package com.example.optalkproject2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

// Inside the FormActivity class
public class FormActivity extends AppCompatActivity {
    private EditText capacityEditText;
    private LinearLayout itemsContainer;
    private Button addItemButton;
    private Button submitButton;
    private List<EditText> itemWeightEditTextList;
    private List<EditText> itemValueEditTextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // Initialize views
        capacityEditText = findViewById(R.id.capacityEditText);
        itemsContainer = findViewById(R.id.itemsContainer);
        addItemButton = findViewById(R.id.addItemButton);
        submitButton = findViewById(R.id.submitButton);

        // Initialize lists to store dynamically added EditTexts
        itemWeightEditTextList = new ArrayList<>();
        itemValueEditTextList = new ArrayList<>();

        // Set up click listener for Add Item button
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add a new item field to the itemsContainer
                addItemField();
            }
        });

        // Set up click listener for submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Solve knapsack problem and display results
                solveKnapsackAndDisplayResults();
            }
        });
    }

    // Method to add a new item field dynamically
    private void addItemField() {
        int itemCount = itemsContainer.getChildCount() + 1; // Calculate the item count

        // Create new LinearLayout to hold item label and EditTexts
        LinearLayout itemLayout = new LinearLayout(FormActivity.this);
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        // Create new TextView for item label
        TextView itemLabel = new TextView(FormActivity.this);
        itemLabel.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        itemLabel.setText("Item " + itemCount + ": ");
        itemLayout.addView(itemLabel);

        // Create new EditTexts for weight and value inputs
        EditText weightEditText = new EditText(FormActivity.this);
        EditText valueEditText = new EditText(FormActivity.this);

        // Set attributes for the EditTexts
        weightEditText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
        ));
        weightEditText.setHint("Weight");

        valueEditText.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1
        ));
        valueEditText.setHint("Value");

        // Add the EditTexts to the itemLayout
        itemLayout.addView(weightEditText);
        itemLayout.addView(valueEditText);

        // Add the itemLayout to the itemsContainer
        itemsContainer.addView(itemLayout);

        // Add the EditTexts to the lists for later access
        itemWeightEditTextList.add(weightEditText);
        itemValueEditTextList.add(valueEditText);
    }



    // Method to solve knapsack problem and display results
    // Method to solve knapsack problem and display results
    private void solveKnapsackAndDisplayResults() {
        // Get capacity
        int capacity = Integer.parseInt(capacityEditText.getText().toString());

        // Create lists to store items and their labels
        List<Integer> weights = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        List<String> itemLabels = new ArrayList<>();

        // Populate item weights, values, and labels lists from EditTexts
        for (int i = 0; i < itemWeightEditTextList.size(); i++) {
            EditText weightEditText = itemWeightEditTextList.get(i);
            EditText valueEditText = itemValueEditTextList.get(i);
            String weightStr = weightEditText.getText().toString().trim();
            String valueStr = valueEditText.getText().toString().trim();
            if (!weightStr.isEmpty() && !valueStr.isEmpty()) {
                int weight = Integer.parseInt(weightStr);
                int value = Integer.parseInt(valueStr);
                weights.add(weight);
                values.add(value);
                itemLabels.add("Item " + (i + 1)); // Correctly label each item
            }
        }

        // Solve knapsack problem
        KnapsackResult knapsackResult = solveKnapsack(capacity, weights, values);

        // Display results
        if (knapsackResult != null) {
            displayKnapsackResult(knapsackResult, itemLabels); // Pass item labels to displayKnapsackResult method
        } else {
            // Handle error
            Toast.makeText(FormActivity.this, "Error: Unable to solve knapsack problem.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to solve knapsack problem
    private KnapsackResult solveKnapsack(int capacity, List<Integer> weights, List<Integer> values) {
        int n = weights.size();
        int[][] dp = new int[n + 1][capacity + 1];

        // Build DP table
        for (int i = 1; i <= n; i++) {
            int weight = weights.get(i - 1);
            int value = values.get(i - 1);
            for (int w = 1; w <= capacity; w++) {
                if (weight <= w) {
                    dp[i][w] = Math.max(value + dp[i - 1][w - weight], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Trace back to find selected items
        List<Integer> selectedItems = new ArrayList<>();
        int w = capacity;
        for (int i = n; i > 0 && w > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) {
                selectedItems.add(i - 1);
                w -= weights.get(i - 1);
            }
        }

        // Calculate total value
        int totalValue = dp[n][capacity];

        // Return result
        return new KnapsackResult(selectedItems, totalValue);
    }

    // Method to display knapsack result
    // Method to display knapsack result
// Method to display knapsack result with item labels
    private void displayKnapsackResult(KnapsackResult result, List<String> itemLabels) {
        StringBuilder resultText = new StringBuilder("Selected items:\n");
        List<Integer> selectedItems = result.getSelectedItems();
        for (int i = 0; i < selectedItems.size(); i++) {
            int index = selectedItems.get(i);
            String itemLabel = itemLabels.get(index); // Get the corresponding item label
            EditText weightEditText = itemWeightEditTextList.get(index);
            EditText valueEditText = itemValueEditTextList.get(index);
            int weight = Integer.parseInt(weightEditText.getText().toString().trim());
            int value = Integer.parseInt(valueEditText.getText().toString().trim());
            resultText.append(itemLabel).append(": Weight: ").append(weight).append(", Value: ").append(value).append("\n");
        }
        resultText.append("Total value: ").append(result.getTotalValue());

        // Display the result in the resultTextView
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText(resultText.toString());
    }

}
