package com.example.optalkproject2;

import java.util.List;

// KnapsackResult.java
public class KnapsackResult {
    private List<Integer> selectedItems;
    private int totalValue;

    public KnapsackResult(List<Integer> selectedItems, int totalValue) {
        this.selectedItems = selectedItems;
        this.totalValue = totalValue;
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    public int getTotalValue() {
        return totalValue;
    }
}
