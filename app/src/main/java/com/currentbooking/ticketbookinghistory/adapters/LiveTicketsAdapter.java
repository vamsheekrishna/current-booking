package com.currentbooking.ticketbookinghistory.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.models.TicketViewModel;

import java.util.List;

/**
 * Created by Satya Seshu on 29/06/20.
 */
public class LiveTicketsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TicketViewModel> liveTicketsList;
    private LayoutInflater layoutInflater;

    private int LIVE_TICKETS_APPROVED = 0;
    private int LIVE_TICKETS_PENDING = 1;
    private int LIVE_TICKETS_REJECTED = 2;
    private int LIVE_TICKETS_EXPIRED = 3;
    View.OnClickListener onClickListener;
    public LiveTicketsAdapter(Context context, List<TicketViewModel> liveTicketsList, View.OnClickListener _onClickListener) {
        layoutInflater = LayoutInflater.from(context);
        this.liveTicketsList = liveTicketsList;
        onClickListener = _onClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        TicketViewModel liveTicketsModel = liveTicketsList.get(position);
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
        View itemView;
        if (viewType == LIVE_TICKETS_APPROVED) {
            itemView = layoutInflater.inflate(R.layout.live_tickets_approved_list_items, parent, false);
            itemView.setOnClickListener(onClickListener);
            return new ApprovedViewHolder(itemView);
        } else if (viewType == LIVE_TICKETS_PENDING) {
            itemView = layoutInflater.inflate(R.layout.live_tickets_pending_list_items, parent, false);
            itemView.setOnClickListener(onClickListener);
            return new PendingViewHolder(itemView);
        } else if (viewType == LIVE_TICKETS_REJECTED) {
            itemView = layoutInflater.inflate(R.layout.live_tickets_rejected_list_items, parent, false);
            itemView.setOnClickListener(onClickListener);
            return new RejectedViewHolder(itemView);
        } else {
            itemView = layoutInflater.inflate(R.layout.live_tickets_expired_list_items, parent, false);
            itemView.setOnClickListener(onClickListener);
            return new ExpiredViewHolder(itemView);
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
