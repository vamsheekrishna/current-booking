package com.currentbooking.ticketbookinghistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.models.LiveTicketsModel;

import java.util.List;

/**
 * Created by Satya Seshu on 29/06/20.
 */
public class LiveTicketsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LiveTicketsModel> liveTicketsList;
    private LayoutInflater layoutInflater;

    private int LIVE_TICKETS_APPROVED = 0;
    private int LIVE_TICKETS_PENDING = 1;
    private int LIVE_TICKETS_REJECTED = 2;
    private int LIVE_TICKETS_EXPIRED = 3;

    public LiveTicketsAdapter(Context context, List<LiveTicketsModel> liveTicketsList) {
        layoutInflater = LayoutInflater.from(context);
        this.liveTicketsList = liveTicketsList;
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        LiveTicketsModel liveTicketsModel = liveTicketsList.get(position);
        if (liveTicketsModel.getStatus() == 0) {
            return LIVE_TICKETS_APPROVED;
        } else if (liveTicketsModel.getStatus() == 1) {
            return LIVE_TICKETS_PENDING;
        } else if (liveTicketsModel.getStatus() == 2) {
            return LIVE_TICKETS_REJECTED;
        } else {
            return LIVE_TICKETS_EXPIRED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LIVE_TICKETS_APPROVED) {
            View approvedView = layoutInflater.inflate(R.layout.live_tickets_approved_list_items, parent, false);
            return new ApprovedViewHolder(approvedView);
        } else if (viewType == LIVE_TICKETS_PENDING) {
            View pendingView = layoutInflater.inflate(R.layout.live_tickets_pending_list_items, parent, false);
            return new PendingViewHolder(pendingView);
        } else if (viewType == LIVE_TICKETS_REJECTED) {
            View rejectedView = layoutInflater.inflate(R.layout.live_tickets_rejected_list_items, parent, false);
            return new RejectedViewHolder(rejectedView);
        } else {
            View expiredView = layoutInflater.inflate(R.layout.live_tickets_expired_list_items, parent, false);
            return new ExpiredViewHolder(expiredView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return liveTicketsList.size();
    }

    static class ApprovedViewHolder extends RecyclerView.ViewHolder {

        public ApprovedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class PendingViewHolder extends RecyclerView.ViewHolder {

        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class RejectedViewHolder extends RecyclerView.ViewHolder {

        public RejectedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class ExpiredViewHolder extends RecyclerView.ViewHolder {

        public ExpiredViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
