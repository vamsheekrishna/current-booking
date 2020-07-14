package com.currentbooking.ticketbookinghistory.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.models.PassengerDetailsModel;

import java.util.List;

/**
 * Created by Satya Seshu on 03/07/20.
 */
public class PassengerDetailsAdapter extends RecyclerView.Adapter<PassengerDetailsAdapter.PassengerDetailsViewHolder> {

    private List<PassengerDetailsModel> listData;
    private LayoutInflater layoutInflater;

    public PassengerDetailsAdapter(Context context, List<PassengerDetailsModel> listData) {
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
        PassengerDetailsModel passengerDetailsModel = listData.get(position);
        holder.tvConcessionNameField.setText(passengerDetailsModel.getType());
        holder.tvPassengerAgeField.setText(passengerDetailsModel.getAge());
        String concession = passengerDetailsModel.getConcessionCD();
        if (TextUtils.isEmpty(concession)) {
            concession = "N/A";
        }
        holder.tvConcessionNameField.setText(concession);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static class PassengerDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView tvPassengerTypeField;
        TextView tvPassengerAgeField;
        TextView tvConcessionNameField;

        public PassengerDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPassengerTypeField = itemView.findViewById(R.id.tv_passenger_type_field);
            tvPassengerAgeField = itemView.findViewById(R.id.tv_passenger_age_field);
            tvConcessionNameField = itemView.findViewById(R.id.tv_passenger_concession_type_field);
        }
    }
}
