package com.example.banhnhandau.mycooking.comon;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;


/**
 * Created by HoangAnh on 11/02/2017.
 */
public class TransformationCustom implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int targetWidth = 320;

        double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
        int targetHeight = (int) (targetWidth * aspectRatio);
        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
        if (result != source) {
            // Same bitmap is returned if sizes are the same
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return "transformation" + " desiredWidth";
    }
}
