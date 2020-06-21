package com.currentbooking.ticketbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.models.ConcessionTypeModel;

import java.util.List;

/**
 * Created by Satya Seshu on 21/06/20.
 */
public class ConcessionAddPassengersAdapter extends RecyclerView.Adapter<ConcessionAddPassengersAdapter.ConcessionTypeViewHolder> {

    private LayoutInflater layoutInflater;
    private List<ConcessionTypeModel> passengersList;
    private CallBackInterface callBackInterface;

    public ConcessionAddPassengersAdapter(Context context, List<ConcessionTypeModel> passengersList, CallBackInterface callBackInterface) {
        layoutInflater = LayoutInflater.from(context);
        this.passengersList = passengersList;
        this.callBackInterface = callBackInterface;
    }

    public void updateItems(List<ConcessionTypeModel> passengersList) {
        this.passengersList = passengersList;
    }

    @NonNull
    @Override
    public ConcessionAddPassengersAdapter.ConcessionTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.pasengers_details_list_items, parent, false);
        return new ConcessionAddPassengersAdapter.ConcessionTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcessionAddPassengersAdapter.ConcessionTypeViewHolder holder, int position) {
        ConcessionTypeModel passengersDetails = passengersList.get(position);
        holder.tvPersonTypeField.setText(passengersDetails.getPersonType());
        holder.tvConcessionTypeField.setText(passengersDetails.getConcessionType());
        holder.deleteConcessionBtnLayoutField.setTag(position);
        holder.deleteConcessionBtnLayoutField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            callBackInterface.callBackReceived(passengersList.get(tag));
        });
    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }

    static class ConcessionTypeViewHolder extends RecyclerView.ViewHolder {

        TextView tvPersonTypeField;
        TextView tvConcessionTypeField;
        LinearLayout deleteConcessionBtnLayoutField;

        public ConcessionTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPersonTypeField = itemView.findViewById(R.id.tv_person_type_field);
            tvConcessionTypeField = itemView.findViewById(R.id.tv_concession_type_field);
            deleteConcessionBtnLayoutField = itemView.findViewById(R.id.delete_concession_code_btn_field);
        }
    }
}
