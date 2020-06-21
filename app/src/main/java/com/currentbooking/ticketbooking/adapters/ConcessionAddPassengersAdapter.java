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
import com.currentbooking.utilits.cb_api.responses.Concession;

import java.util.List;

/**
 * Created by Satya Seshu on 21/06/20.
 */
public class ConcessionAddPassengersAdapter extends RecyclerView.Adapter<ConcessionAddPassengersAdapter.ConcessionTypeViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Concession> passengersList;
    private CallBackInterface callBackInterface;

    public ConcessionAddPassengersAdapter(Context context, List<Concession> passengersList, CallBackInterface callBackInterface) {
        layoutInflater = LayoutInflater.from(context);
        this.passengersList = passengersList;
        this.callBackInterface = callBackInterface;
    }

    public void updateItems(List<Concession> passengersList) {
        this.passengersList = passengersList;
    }

    @NonNull
    @Override
    public ConcessionAddPassengersAdapter.ConcessionTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.pasengers_details_list_items, parent, false);
        ConcessionTypeViewHolder viewHolder = new ConcessionTypeViewHolder(itemView);
        viewHolder.deleteConcessionBtnLayoutField.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            callBackInterface.callBackReceived(passengersList.get(tag));
        });
        /*viewHolder.rootView.setOnClickListener(v -> {
            int tag = (int) v.getTag();
            ContentModel contentModel = new ContentModel();
            contentModel.setStatus("edit");
            contentModel.setValue(passengersList.get(tag));
            callBackInterface.callBackReceived(contentModel);
        });*/
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ConcessionAddPassengersAdapter.ConcessionTypeViewHolder holder, int position) {
        Concession passengersDetails = passengersList.get(position);
        holder.tvPersonTypeField.setText(passengersDetails.getPersonType());
        holder.tvConcessionTypeField.setText(passengersDetails.getConcessionNM());
        holder.deleteConcessionBtnLayoutField.setTag(position);
    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }

    static class ConcessionTypeViewHolder extends RecyclerView.ViewHolder {

        TextView tvPersonTypeField;
        TextView tvConcessionTypeField;
        LinearLayout deleteConcessionBtnLayoutField;
        //LinearLayout rootView;
        public ConcessionTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            //rootView = itemView.findViewById(R.id.root_view);
            tvPersonTypeField = itemView.findViewById(R.id.tv_person_type_field);
            tvConcessionTypeField = itemView.findViewById(R.id.tv_concession_type_field);
            deleteConcessionBtnLayoutField = itemView.findViewById(R.id.delete_concession_code_btn_field);
        }
    }
}
