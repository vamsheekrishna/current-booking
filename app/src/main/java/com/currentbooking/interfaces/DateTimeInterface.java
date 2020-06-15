package com.currentbooking.interfaces;

public interface DateTimeInterface {

    void onTimeSet(Integer hourOfDay, Integer minute, String amOrPm);

    void onDateSet(Integer year, Integer month, Integer dayOfMonth);
}
