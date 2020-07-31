package com.an.mycalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<String> {

    ArrayList<String> history = new ArrayList<String>();

    public HistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void setHistory(ArrayList<String> items){
        history.addAll(items);
    }

    @Override
    public int getCount() {
        return history.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.history_item, parent,false);
        }

        TextView tv_hist = convertView.findViewById(R.id.tv_histItem);

        String hist = history.get(position);
        tv_hist.setText(hist);

        return convertView;
    }
}
