package com.currentbooking.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.home.dummy.DummyContent;

import java.util.List;

public class MyServiceRecyclerViewAdapter extends RecyclerView.Adapter<MyServiceRecyclerViewAdapter.ViewHolder> {

    private final List<DummyContent.DummyItem> mValues;
    private final View.OnClickListener mListener;

    public MyServiceRecyclerViewAdapter(List<DummyContent.DummyItem> items, View.OnClickListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_services_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setImageResource(mValues.get(position).image);
        holder.mTitle.setText(mValues.get(position).title);
        holder.mView.setOnClickListener(mListener);
        holder.mView.setTag(holder.mItem);
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    mListener.onSelectedServiceInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mIdView;
        public final TextView mTitle;
        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.view_image);
            mTitle = view.findViewById(R.id.view_title);
        }
    }
}
