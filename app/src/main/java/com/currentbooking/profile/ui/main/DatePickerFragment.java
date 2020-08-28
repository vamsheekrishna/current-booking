package com.currentbooking.profile.ui.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.currentbooking.interfaces.DateTimeInterface;
import com.currentbooking.utilits.Constants;

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
            year = extras.getInt(Constants.KEY_YEAR);
            month = extras.getInt(Constants.KEY_MONTH);
            dayOfMonth = extras.getInt(Constants.KEY_DAY_OF_MONTH);
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
