package com.currentbooking.ticketbookinghistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.Constants;

import java.util.List;

/**
 * Created by Satya Seshu on 29/06/20.
 */
public class LiveTicketsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MyTicketInfo> liveTicketsList;
    private LayoutInflater layoutInflater;

    private int LIVE_TICKETS_APPROVED = 0;
    private int LIVE_TICKETS_PENDING = 1;
    private int LIVE_TICKETS_REJECTED = 2;
    private Context context;

    public LiveTicketsAdapter(Context context, List<MyTicketInfo> liveTicketsList) {
        layoutInflater = LayoutInflater.from(context);
        this.liveTicketsList = liveTicketsList;
        this.context = context;
    }

    public void updateTickets(List<MyTicketInfo> liveTicketsList) {
        this.liveTicketsList = liveTicketsList;
    }

    @Override
    public int getItemViewType(int position) {
        MyTicketInfo liveTicketsModel = liveTicketsList.get(position);
        if (liveTicketsModel.getTicket_status().equalsIgnoreCase(Constants.KEY_APPROVED)) {
            return LIVE_TICKETS_APPROVED;
        } else if (liveTicketsModel.getTicket_status().equalsIgnoreCase(Constants.KEY_PENDING)) {
            return LIVE_TICKETS_PENDING;
        } else if (liveTicketsModel.getTicket_status().equalsIgnoreCase(Constants.KEY_REJECTED)) {
            return LIVE_TICKETS_REJECTED;
        } else {
            return 3;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == LIVE_TICKETS_APPROVED) {
            itemView = layoutInflater.inflate(R.layout.live_tickets_approved_list_items, parent, false);
            return new ApprovedViewHolder(itemView);
        } else if (viewType == LIVE_TICKETS_PENDING) {
            itemView = layoutInflater.inflate(R.layout.live_tickets_pending_list_items, parent, false);
            return new PendingViewHolder(itemView);
        } else if (viewType == LIVE_TICKETS_REJECTED) {
            itemView = layoutInflater.inflate(R.layout.live_tickets_rejected_list_items, parent, false);
            return new RejectedViewHolder(itemView);
        } else {
            itemView = layoutInflater.inflate(R.layout.live_tickets_expired_list_items, parent, false);
            return new ExpiredViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyTicketInfo availableTicket = liveTicketsList.get(position);
        if (getItemViewType(position) == LIVE_TICKETS_APPROVED) { // approved
            ApprovedViewHolder approvedViewHolder = (ApprovedViewHolder) holder;
            String busRouteName = String.format("%s %s", availableTicket.getOperator_name().toUpperCase(), availableTicket.getBus_service_no());
            approvedViewHolder.tvBusRouteNameField.setText(busRouteName);
            String busRoute = String.format("%s to %s", availableTicket.getFrom_stop(), availableTicket.getTo_stop());
            approvedViewHolder.tvBusRouteField.setText(busRoute);
            if(availableTicket.getBus_type_name().equalsIgnoreCase("")){
                approvedViewHolder.tv_bus_type_field.setVisibility(View.GONE);
            }else{
                approvedViewHolder.tv_bus_type_field.setText("Bus type: "+availableTicket.getBus_type_name()); }
            approvedViewHolder.tv_bus_type_field.setText("Bus type: "+availableTicket.getBus_type_name());
            approvedViewHolder.tvJourneyStartTimeField.setText(availableTicket.getStart_time());
            approvedViewHolder.tvJourneyEndTimeField.setText(availableTicket.getDrop_time());
            String journeyHrs = String.format("%s Hrs", availableTicket.getHours());
            approvedViewHolder.tvJourneyHrsField.setText(journeyHrs);
            String ticketNo = String.format("%s %s", context.getString(R.string.ticket_number), availableTicket.getTicket_no());
            approvedViewHolder.tvTicketNoField.setText(ticketNo);
        } else if (getItemViewType(position) == LIVE_TICKETS_PENDING) { // pending
            PendingViewHolder pendingViewHolder = (PendingViewHolder) holder;
            String busRouteName = String.format("%s %s", availableTicket.getOperator_name().toUpperCase(), availableTicket.getBus_service_no());
            pendingViewHolder.tvBusRouteNameField.setText(busRouteName);
            String busRoute = String.format("%s to %s", availableTicket.getFrom_stop(), availableTicket.getTo_stop());
            pendingViewHolder.tvBusRouteField.setText(busRoute);
            if(availableTicket.getBus_type_name().equalsIgnoreCase("")){
                pendingViewHolder.tv_bus_type_field.setVisibility(View.GONE);
            }else{
                pendingViewHolder.tv_bus_type_field.setText("Bus type: "+availableTicket.getBus_type_name()); }
            pendingViewHolder.tvJourneyStartTimeField.setText(availableTicket.getStart_time());
            pendingViewHolder.tvJourneyEndTimeField.setText(availableTicket.getDrop_time());
            String journeyHrs = String.format("%s Hrs", availableTicket.getHours());
            pendingViewHolder.tvJourneyHrsField.setText(journeyHrs);
            String ticketNo = String.format("%s %s", context.getString(R.string.ticket_number), availableTicket.getTicket_no());
            pendingViewHolder.tvTicketNoField.setText(ticketNo);
        } else if (getItemViewType(position) == LIVE_TICKETS_REJECTED) { // rejected
            RejectedViewHolder rejectedViewHolder = (RejectedViewHolder) holder;
            String busRouteName = String.format("%s %s", availableTicket.getOperator_name().toUpperCase(), availableTicket.getBus_service_no());
            rejectedViewHolder.tvBusRouteNameField.setText(busRouteName);
            String busRoute = String.format("%s to %s", availableTicket.getFrom_stop(), availableTicket.getTo_stop());
            if(availableTicket.getBus_type_name().equalsIgnoreCase("")){
                rejectedViewHolder.tv_bus_type_field.setVisibility(View.GONE);
            }else{
                rejectedViewHolder.tv_bus_type_field.setText("Bus type: "+availableTicket.getBus_type_name()); }
            rejectedViewHolder.tvJourneyStartTimeField.setText(availableTicket.getStart_time());
            rejectedViewHolder.tvJourneyEndTimeField.setText(availableTicket.getDrop_time());
            String journeyHrs = String.format("%s Hrs", availableTicket.getHours());
            rejectedViewHolder.tvJourneyHrsField.setText(journeyHrs);
            String ticketNo = String.format("%s %s", context.getString(R.string.ticket_number), availableTicket.getTicket_no());
            rejectedViewHolder.tvTicketNoField.setText(ticketNo);
        } else { // expired
            ExpiredViewHolder expiredViewHolder = (ExpiredViewHolder) holder;
            String busRouteName = String.format("%s %s", availableTicket.getOperator_name().toUpperCase(), availableTicket.getBus_service_no());
            expiredViewHolder.tvBusRouteNameField.setText(busRouteName);
            String busRoute = String.format("%s to %s", availableTicket.getFrom_stop(), availableTicket.getTo_stop());
            expiredViewHolder.tvBusRouteField.setText(busRoute);
            if(availableTicket.getBus_type_name().equalsIgnoreCase("")){
                expiredViewHolder.tv_bus_type_field.setVisibility(View.GONE);
            }else{
                expiredViewHolder.tv_bus_type_field.setText("Bus type: "+availableTicket.getBus_type_name()); }
            expiredViewHolder.tvJourneyStartTimeField.setText(availableTicket.getStart_time());
            expiredViewHolder.tvJourneyEndTimeField.setText(availableTicket.getDrop_time());
            String journeyHrs = String.format("%s Hrs", availableTicket.getHours());
            expiredViewHolder.tvJourneyHrsField.setText(journeyHrs);
            String ticketNo = String.format("%s %s", context.getString(R.string.ticket_number), availableTicket.getTicket_no());
            expiredViewHolder.tvTicketNoField.setText(ticketNo);
        }
    }

    @Override
    public int getItemCount() {
        return liveTicketsList.size();
    }

    static class ApprovedViewHolder extends RecyclerView.ViewHolder {
        TextView tvBusRouteNameField;
        TextView tvBusRouteField;
        TextView tvJourneyStartTimeField;
        TextView tvJourneyEndTimeField;
        TextView tvJourneyHrsField;
        TextView tvTicketNoField;
        TextView tv_bus_type_field;

        public ApprovedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBusRouteNameField = itemView.findViewById(R.id.tv_bus_route_name_field);
            tvBusRouteField = itemView.findViewById(R.id.tv_bus_route_field);
            tv_bus_type_field = itemView.findViewById(R.id.tv_bus_type_field);
            tvJourneyStartTimeField = itemView.findViewById(R.id.tv_bus_journey_start_time_field);
            tvJourneyEndTimeField = itemView.findViewById(R.id.tv_bus_journey_end_time_field);
            tvJourneyHrsField = itemView.findViewById(R.id.tv_bus_journey_hours_field);
            tvTicketNoField = itemView.findViewById(R.id.tv_ticket_number_field);
        }
    }

    static class PendingViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpiresInTimeField;
        TextView tvBusRouteNameField;
        TextView tvBusRouteField;
        TextView tvJourneyStartTimeField;
        TextView tvJourneyEndTimeField;
        TextView tvJourneyHrsField;
        TextView tvTicketNoField;
        TextView tv_bus_type_field;


        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpiresInTimeField = itemView.findViewById(R.id.tv_expires_in_time_field);
            tvBusRouteNameField = itemView.findViewById(R.id.tv_bus_route_name_field);
            tvBusRouteField = itemView.findViewById(R.id.tv_bus_route_field);
            tv_bus_type_field = itemView.findViewById(R.id.tv_bus_type_field);

            tvJourneyStartTimeField = itemView.findViewById(R.id.tv_bus_journey_start_time_field);
            tvJourneyEndTimeField = itemView.findViewById(R.id.tv_bus_journey_end_time_field);
            tvJourneyHrsField = itemView.findViewById(R.id.tv_bus_journey_hours_field);
            tvTicketNoField = itemView.findViewById(R.id.tv_ticket_number_field);
        }
    }

    static class RejectedViewHolder extends RecyclerView.ViewHolder {
        TextView tvExpiresInTimeField;
        TextView tvBusRouteNameField;
        TextView tvBusRouteField;
        TextView tvJourneyStartTimeField;
        TextView tv_bus_type_field;
        TextView tvJourneyEndTimeField;
        TextView tvJourneyHrsField;
        TextView tvTicketNoField;

        public RejectedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvExpiresInTimeField = itemView.findViewById(R.id.tv_expires_in_time_field);
            tvBusRouteNameField = itemView.findViewById(R.id.tv_bus_route_name_field);
            tvBusRouteField = itemView.findViewById(R.id.tv_bus_route_field);
            tv_bus_type_field = itemView.findViewById(R.id.tv_bus_type_field);

            tvJourneyStartTimeField = itemView.findViewById(R.id.tv_bus_journey_start_time_field);
            tvJourneyEndTimeField = itemView.findViewById(R.id.tv_bus_journey_end_time_field);
            tvJourneyHrsField = itemView.findViewById(R.id.tv_bus_journey_hours_field);
            tvTicketNoField = itemView.findViewById(R.id.tv_ticket_number_field);
        }
    }

    static class ExpiredViewHolder extends RecyclerView.ViewHolder {
        TextView tvBusRouteNameField;
        TextView tvBusRouteField;
        TextView tv_bus_type_field;
        TextView tvJourneyStartTimeField;
        TextView tvJourneyEndTimeField;
        TextView tvJourneyHrsField;
        TextView tvTicketNoField;

        public ExpiredViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBusRouteNameField = itemView.findViewById(R.id.tv_bus_route_name_field);
            tvBusRouteField = itemView.findViewById(R.id.tv_bus_route_field);
            tv_bus_type_field = itemView.findViewById(R.id.tv_bus_type_field);

            tvJourneyStartTimeField = itemView.findViewById(R.id.tv_bus_journey_start_time_field);
            tvJourneyEndTimeField = itemView.findViewById(R.id.tv_bus_journey_end_time_field);
            tvJourneyHrsField = itemView.findViewById(R.id.tv_bus_journey_hours_field);
            tvTicketNoField = itemView.findViewById(R.id.tv_ticket_number_field);
        }
    }
}
