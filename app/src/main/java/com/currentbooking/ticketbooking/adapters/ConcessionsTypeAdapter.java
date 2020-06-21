package com.currentbooking.ticketbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.utilits.cb_api.responses.Concession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Satya Seshu on 17/06/20.
 */
public class ConcessionsTypeAdapter extends RecyclerView.Adapter<ConcessionsTypeAdapter.ConcessionTypeViewHolder>
        implements Filterable {

    private LayoutInflater layoutInflater;
    private List<Concession> concessionsList;
    private List<Concession> filteredListData;
    private MyFilter myFilter;

    public ConcessionsTypeAdapter(Context context, List<Concession> concessionsList) {
        layoutInflater = LayoutInflater.from(context);
        this.concessionsList = concessionsList;
        this.filteredListData = new ArrayList<>();
        this.filteredListData.addAll(concessionsList);
    }

    @NonNull
    @Override
    public ConcessionTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.concessions_type_list_items, parent, false);
        return new ConcessionTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConcessionTypeViewHolder holder, int position) {
        Concession concessionModel = filteredListData.get(position);
        holder.concessionNameField.setText(concessionModel.getConcessionNM());
    }

    @Override
    public int getItemCount() {
        return filteredListData.size();
    }

    static class ConcessionTypeViewHolder extends RecyclerView.ViewHolder {

        TextView concessionNameField;

        public ConcessionTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            concessionNameField = itemView.findViewById(R.id.tv_concession_type_field);
        }
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                filteredListData.clear();
                for (Concession concessionDetails : concessionsList) {
                    if (concessionDetails.getConcessionNM().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(concessionDetails);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<Concession>) results.values;
            notifyDataSetChanged();
        }
    }
}
