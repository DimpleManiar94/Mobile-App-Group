package com.example.dimple.fastpath;

public class Model {
    private boolean isSelected;
    private String items;

    public String getitems() {
        return items;
    }

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setitems(String items) {
        this.items = items;
    }
}