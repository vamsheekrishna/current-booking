package com.currentbooking.ticketbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;

import java.util.List;

public class SelectBusesAdapter extends RecyclerView.Adapter<SelectBusesAdapter.SelectBusesViewHolder> {

    private LayoutInflater layoutInflater;
    private List<Object> listData;

    public SelectBusesAdapter(Context context, List<Object> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @NonNull
    @Override
    public SelectBusesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.select_buses_list_items, parent, false);
        return new SelectBusesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectBusesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    static public class SelectBusesViewHolder extends RecyclerView.ViewHolder {

        TextView tvBusRouteNameField;
        TextView tvBusTypeField;
        TextView tvBusRouteField;
        TextView tvBusStartTimeField;
        TextView tvBusEndTimeField;
        TextView tvBusFareField;
        Button btnBookNowField;
        TextView tvJourneyHrsField;

        public SelectBusesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBusRouteNameField = itemView.findViewById(R.id.tv_route_name_field);
            tvBusTypeField = itemView.findViewById(R.id.tv_bus_type_field);
            tvBusRouteField = itemView.findViewById(R.id.tv_bus_route_field);
            tvBusStartTimeField = itemView.findViewById(R.id.tv_bus_journey_start_time_field);
            tvBusEndTimeField = itemView.findViewById(R.id.tv_bus_journey_end_time_field);
            tvBusFareField = itemView.findViewById(R.id.tv_bus_fare_price_field);
            btnBookNowField = itemView.findViewById(R.id.btn_book_now_field);
            tvJourneyHrsField = itemView.findViewById(R.id.tv_bus_journey_hours_field);

        }
    }
}