package com.an.cinemaheaven;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class galleryViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    ImageView playIcon;

    public galleryViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.iv_galleryItem);
        playIcon = (ImageView) itemView.findViewById(R.id.iv_playIcon);
    }



    public void setIem(galleryItem item){
        imageView.setImageBitmap(item.res);
        if(item.video){
            playIcon.setVisibility(View.VISIBLE);
        }
    }
}
