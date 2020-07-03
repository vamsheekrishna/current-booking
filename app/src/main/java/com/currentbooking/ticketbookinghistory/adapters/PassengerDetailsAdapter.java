package com.currentbooking.ticketbookinghistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;

import java.util.List;

/**
 * Created by Satya Seshu on 03/07/20.
 */
public class PassengerDetailsAdapter extends RecyclerView.Adapter<PassengerDetailsAdapter.PassengerDetailsViewHolder> {

    private List<Object> listData;
    private LayoutInflater layoutInflater;

    public PassengerDetailsAdapter(Context context, List<Object> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @NonNull
    @Override
    public PassengerDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.passenger_details_list_items, parent, false);
        return new PassengerDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerDetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class PassengerDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView tvPassengerTypeField;
        TextView tvPassengerAgeField;
        TextView tvConcessionTypeField;

        public PassengerDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPassengerTypeField = itemView.findViewById(R.id.tv_passenger_type_field);
            tvPassengerAgeField = itemView.findViewById(R.id.tv_passenger_age_field);
            tvConcessionTypeField = itemView.findViewById(R.id.tv_passenger_concession_type_field);
        }
    }
}
