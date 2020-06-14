package com.currentbooking.ticketbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.utilits.cb_api.responses.BusObject;

import java.util.ArrayList;
import java.util.List;

public class SelectBusesAdapter extends RecyclerView.Adapter<SelectBusesAdapter.SelectBusesViewHolder> {

    private LayoutInflater layoutInflater;
    private List<BusObject> listData;
    private String toText;
    private String busOperatorName;
    View.OnClickListener mClick;
    public SelectBusesAdapter(View.OnClickListener click , Context context, ArrayList<BusObject> listData, String busOperatorName) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
        this.busOperatorName = busOperatorName;
        toText = context.getString(R.string.to);
        mClick = click;
    }

    @NonNull
    @Override
    public SelectBusesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.select_buses_list_items, parent, false);
        SelectBusesViewHolder selectBusesViewHolder =  new SelectBusesViewHolder(itemView);
        selectBusesViewHolder.btnBookNowField.setOnClickListener(mClick);
        return selectBusesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectBusesViewHolder holder, int position) {
        BusObject busObject = listData.get(position);
        String busRoute = String.format("%s %s %s", busObject.getSourceStageName(), toText, busObject.getDestinationStageName());
        holder.tvBusRouteField.setText(busRoute);
        holder.tvBusTypeField.setText(busObject.getBusTypeNM());
        String busRouteName = busOperatorName +" "+ busObject.getRouteNumber();// String.format("MSRTC BUS %s", busObject.getRouteNumber());
        holder.tvBusRouteNameField.setText(busRouteName);
        holder.btnBookNowField.setTag(busObject);
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
        TextView btnBookNowField;
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