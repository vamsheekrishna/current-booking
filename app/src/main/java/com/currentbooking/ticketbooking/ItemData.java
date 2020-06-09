package com.currentbooking.ticketbooking;

public class ItemData {

    String itemName;
    int index;

    ItemData(String name, int id) {
        itemName = name;
        index = id;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
