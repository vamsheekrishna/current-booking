package com.currentbooking.ticketbooking.adapters;

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
 * Created by Satya Seshu on 17/06/20.
 */
public class ConcessionsTypeAdapter extends RecyclerView.Adapter<ConcessionsTypeAdapter.ConcessionTypeViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Object> concessionsList;

    public ConcessionsTypeAdapter(Context context, List<Object> concessionsList) {
        layoutInflater = LayoutInflater.from(context);
        this.concessionsList = concessionsList;
    }

    @NonNull
    @Override
    public ConcessionTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.concessions_type_list_items, parent, false);
        return new ConcessionTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcessionTypeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return concessionsList.size();
    }

    public static class ConcessionTypeViewHolder extends RecyclerView.ViewHolder {

        TextView concessionNameField;
        TextView concessionDescriptionField;
        TextView concessionAmountField;

        public ConcessionTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            concessionNameField = itemView.findViewById(R.id.tv_concession_name_field);
            concessionDescriptionField = itemView.findViewById(R.id.tv_concession_description_field);
            concessionAmountField = itemView.findViewById(R.id.tv_concession_amount_field);
        }
    }
}