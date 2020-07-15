package com.currentbooking.utilits;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;

/**
 * Created by Satya Seshu on 15/07/20.
 */
public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MvvmView.View mvvmView;

    public MyViewModelFactory(MvvmView.View mvvmView) {
        this.mvvmView = mvvmView;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TicketBookingViewModel(mvvmView);
    }
}