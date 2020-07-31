package com.an.cinemaheaven;


import android.graphics.Bitmap;

public class galleryItem {
    Bitmap res;
    boolean video = false;
    galleryItem(Bitmap bitmap, boolean video){
        this.res = bitmap;
        this.video= video;
    }
}
