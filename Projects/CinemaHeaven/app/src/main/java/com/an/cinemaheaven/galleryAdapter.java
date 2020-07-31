package com.an.cinemaheaven;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class galleryAdapter extends RecyclerView.Adapter<galleryViewHolder> {

    Context context;

    ArrayList<galleryItem> items = new ArrayList<galleryItem>();

    public galleryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public galleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.gallery_item,parent,false);
        return new galleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull galleryViewHolder holder, int position) {
        galleryItem item = items.get(position);
        holder.setIem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(galleryItem item){
       items.add(item);
    }
}
