package com.example.parallaks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Background {
    Bitmap bitmap;
    Bitmap bitmapReversed;

    int width;
    int height;
    boolean reversedFirst;
    float speed;

    int xClip;
    int startY;
    int endY;

    Background(Context context, int screenWidth, int screenHeight, String bitmapName, int sY, int eY, float s) {
        //resID is the image file name used as string
        int resID = context.getResources().getIdentifier(bitmapName,"drawable",context.getPackageName());

        //load the bitmap using the ID
        bitmap = BitmapFactory.decodeResource(context.getResources(),resID);
        //the reversed or regular backgrounds are drawn first
        reversedFirst = false;

        //initialize animation variables

        //where to clip the bitmap
        xClip = 0;

        //position the background vertically
        startY = sY * (screenHeight / 100);
        endY = eY * (screenHeight/ 100);
        speed = s;

        //create the bitmap
        bitmap = Bitmap.createScaledBitmap(bitmap,screenWidth,(endY - startY), true);

        //save width and height for later use
        width = bitmap.getWidth();
        height = bitmap.getHeight();


        //create the mirror image of the background
        Matrix matrix = new Matrix();
        matrix.setScale(-1,1);
        bitmapReversed = Bitmap.createBitmap(bitmap,0,0, width,height,matrix,true);

    }


    public void update(long fps) {
        //move the clipping position and reverse if necessary
        xClip -= speed / fps;
        if (xClip >= width) {
            xClip = 0;
            reversedFirst = !reversedFirst;
        } else if (xClip <= 0) {
            xClip = width;
            reversedFirst = !reversedFirst;
        }



    }
}