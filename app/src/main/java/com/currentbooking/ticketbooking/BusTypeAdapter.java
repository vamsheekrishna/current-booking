package com.currentbooking.ticketbooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;

import java.util.ArrayList;

public class BusTypeAdapter extends RecyclerView.Adapter<BusTypeAdapter.BusTypeViewHolder> {
    ArrayList<String> busTypes;

    public BusTypeAdapter(ArrayList<String> busTypes) {
        this.busTypes = busTypes;
    }


    @NonNull
    @Override
    public BusTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_type_view_row, parent, false);
        return new BusTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusTypeViewHolder holder, int position) {
        holder.textView.setText(busTypes.get(position));
        if(position == 0 ) {
            holder.textView.setBackgroundResource(R.drawable.edit_text_left_rounded_edges);
        } else if(position == busTypes.size()-1) {
            holder.textView.setBackgroundResource(R.drawable.edit_text_right_rounded_edges);
        } else {
            holder.textView.setBackgroundResource(R.drawable.edit_text_stright_edges);
        }
    }

    @Override
    public int getItemCount() {
        return busTypes.size();
    }

    public static class BusTypeViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public BusTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
