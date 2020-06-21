package com.currentbooking.ticketbooking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.currentbooking.R;
import com.currentbooking.utilits.cb_api.responses.Concession;

import java.util.List;

/**
 * Created by Satya Seshu on 20/06/20.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    private List<Object> listData;
    private LayoutInflater layoutInflater;

    public CustomSpinnerAdapter(Context context, List<Object> listData) {
        layoutInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    public void updateItems(@NonNull List<Object> objects) {
        this.listData = objects;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolderItem;
        if (convertView == null) {
            viewHolderItem = new ViewHolderItem();
            convertView = layoutInflater.inflate(R.layout.custom_spinner_items_view, parent, false);
            viewHolderItem.textView = convertView.findViewById(R.id.custom_text_field);
            convertView.setTag(viewHolderItem);
        } else {
            viewHolderItem = (ViewHolderItem) convertView.getTag();
        }
        Object dataObject = listData.get(position);
        if (dataObject instanceof String) {
            String value = (String) dataObject;
            viewHolderItem.textView.setText(value);
        } else if (dataObject instanceof Concession) {
            Concession concessionDetails = (Concession) dataObject;
            viewHolderItem.textView.setText(concessionDetails.getConcessionNM());
        }
        return convertView;
    }

    public static class ViewHolderItem {
        TextView textView;
    }
}
