package com.currentbooking.profile.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.currentbooking.interfaces.DateTimeInterface;

import java.util.Calendar;
import java.util.Objects;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateTimeInterface dateTimeInterface;
    private int year;
    private int month;
    private int dayOfMonth;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Bundle extras = getArguments();
        if (extras != null) {
            year = extras.getInt("Year");
            month = extras.getInt("Month");
            dayOfMonth = extras.getInt("DayOfMonth");
        }
        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, dayOfMonth);
        dialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        return dialog;
    }

    void setCallBack(DateTimeInterface dateTimeInterface) {
        this.dateTimeInterface = dateTimeInterface;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateTimeInterface.onDateSet(year, month, dayOfMonth);
    }
}
