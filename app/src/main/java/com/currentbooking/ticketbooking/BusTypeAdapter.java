package com.currentbooking.ticketbooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;

public class BusTypeAdapter extends RecyclerView.Adapter<BusTypeAdapter.BusTypeViewHolder> {
    @NonNull
    @Override
    public BusTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_type_view_row, parent, false);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull BusTypeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class BusTypeViewHolder extends RecyclerView.ViewHolder {
        public BusTypeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
