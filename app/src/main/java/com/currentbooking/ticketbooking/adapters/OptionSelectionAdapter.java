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

import java.util.ArrayList;
import java.util.List;

public class OptionSelectionAdapter extends RecyclerView.Adapter<OptionSelectionAdapter.OptionSelectionViewHolder>
        implements Filterable {

    private List<String> listData;
    private LayoutInflater layoutInflater;
    private List<String> filteredListData;
    private MyFilter myFilter;

    public OptionSelectionAdapter(Context context, List<String> listData) {
        this.listData = listData;
        this.filteredListData = new ArrayList<>();
        this.filteredListData.addAll(listData);
        layoutInflater = LayoutInflater.from(context);
    }

    public void updateItems(List<String> listData) {
        this.listData = listData;
        this.filteredListData.clear();
        this.filteredListData.addAll(listData);
    }

    @NonNull
    @Override
    public OptionSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.options_selection_list_items, parent, false);
        return new OptionSelectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionSelectionViewHolder holder, int position) {
        holder.cityNameField.setText(filteredListData.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredListData.size();
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    public static class OptionSelectionViewHolder extends RecyclerView.ViewHolder {

        TextView cityNameField;

        public OptionSelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            cityNameField = itemView.findViewById(R.id.city_name_field);
        }
    }

    private class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                filteredListData.clear();
                for (String location : listData) {
                    if (location.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(location);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
