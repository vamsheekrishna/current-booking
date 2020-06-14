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
import com.currentbooking.ticketbooking.viewmodels.ItemData;
import com.currentbooking.utilits.cb_api.responses.BusStopObject;

import java.util.ArrayList;
import java.util.List;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.OptionSelectionViewHolder>
        implements Filterable {

    private List<BusStopObject> listData;
    private LayoutInflater layoutInflater;
    private List<BusStopObject> filteredListData;
    private MyFilter myFilter;
    private View.OnClickListener onClick;

    public BusStopAdapter(Context context, List<BusStopObject> listData, View.OnClickListener _onClick) {
        this.listData = listData;
        this.filteredListData = new ArrayList<>();
        this.filteredListData.addAll(listData);
        layoutInflater = LayoutInflater.from(context);
        this.onClick = _onClick;
    }

    public void updateItems(List<BusStopObject> listData) {
        this.listData = listData;
        this.filteredListData.clear();
        this.filteredListData.addAll(listData);
    }

    @NonNull
    @Override
    public OptionSelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.options_selection_list_items, parent, false);
        OptionSelectionViewHolder viewHolder = new OptionSelectionViewHolder(itemView);
        viewHolder.cityNameField.setOnClickListener(onClick);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionSelectionViewHolder holder, int position) {
        holder.cityNameField.setText(filteredListData.get(position).getStopName()+" ( "+filteredListData.get(position).getStopCode()+" )");
        holder.cityNameField.setTag(filteredListData.get(position));
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
                for (BusStopObject busStop : listData) {
                    if (busStop.getStopName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredListData.add(busStop);
                    }
                }
            }
            results.count = filteredListData.size();
            results.values = filteredListData;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredListData = (List<BusStopObject>) results.values;
            notifyDataSetChanged();
        }
    }
}
