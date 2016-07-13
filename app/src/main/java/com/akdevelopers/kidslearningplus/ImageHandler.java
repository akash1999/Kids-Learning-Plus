package com.akdevelopers.kidslearningplus;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;


class ImageHandler {

    private final ImageSwitcher mFrame;
    private byte nextImageIndex;
    private byte currentImageIndex;
    private byte prevImageIndex;
    private int[] imageLibrary;     //array to store image resource id's
    private Context mContext;
    private Animation slideInLeft, slideOutLeft,
            slideInRight, slideOutRight;
    /**
     * @param context Context of calling object
     * @param frame Object referring to ImageSwitcher object
     */
    ImageHandler(Context context, ImageSwitcher frame) {
        mFrame = frame;
        mContext = context;
    }

    /**
     * Call this method to initialise imageLibrary array
     *
     * @param a array contains images resource id
     */
    public void setImageLibrary(int[] a) {
        imageLibrary = new int[a.length];
        System.arraycopy(a, 0, imageLibrary, 0, imageLibrary.length);

        currentImageIndex = 0;
        nextImageIndex = 1;
        prevImageIndex = (byte) (a.length - 1);
        initialiseAnimation();
    }

    public void loadFirst() {
        mFrame.setImageResource(imageLibrary[0]);
    }

    private void initialiseAnimation() {
        slideInRight = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_right);
        slideOutRight = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);
        slideInLeft = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left);
        slideOutLeft = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left);
    }

    public void mNextImage() {
        mFrame.setInAnimation(slideInRight);
        mFrame.setOutAnimation(slideOutLeft);

        if (nextImageIndex < imageLibrary.length) {
            mFrame.setImageResource(imageLibrary[nextImageIndex]);
            currentImageIndex = nextImageIndex;
            prevImageIndex = (byte) (currentImageIndex - 1);
            nextImageIndex = (byte) (currentImageIndex + 1);
        } else {
            prevImageIndex = currentImageIndex;
            currentImageIndex = 0;
            nextImageIndex = (byte) (currentImageIndex + 1);
            mFrame.setImageResource(imageLibrary[currentImageIndex]);
        }
    }

    public void mPrevImage() {
        mFrame.setInAnimation(slideInLeft);
        mFrame.setOutAnimation(slideOutRight);

        if (prevImageIndex != -1) {
            mFrame.setImageResource(imageLibrary[prevImageIndex]);
            currentImageIndex = prevImageIndex;
            prevImageIndex = (byte) (currentImageIndex - 1);
            nextImageIndex = (byte) (currentImageIndex + 1);
        } else {
            prevImageIndex = (byte) (imageLibrary.length - 2);
            nextImageIndex = currentImageIndex;
            currentImageIndex = (byte) (prevImageIndex + 1);
            mFrame.setImageResource((imageLibrary[currentImageIndex]));
        }
    }
}
