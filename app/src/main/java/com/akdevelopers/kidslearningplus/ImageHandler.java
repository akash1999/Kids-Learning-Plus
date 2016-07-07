/*
 * Copyright (c) 2016, Akash Chaudhary
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 *
 *
 *
 *
 */


package com.akdevelopers.kidslearningplus;

import android.widget.ImageSwitcher;

/**
 * This class used to switch images from ImageView.
 * <p>
 * HOW TO USE:
 * 1. Create an object of Type ImageHandler passing resource id of ImageView in constructor
 * eg. <code>ImageHandler <object-name> = new ImageHandler(imageview-resource-id);</code>
 * 2. call setImageLibrary() method passing array that contain images resource id as argument
 * Use Method mNextImage to change to next image
 * Use mPrevImage Method to change image to previous image
 *
 * @author Akash Chaudhary
 */
class ImageHandler {

    private final ImageSwitcher frame;
    private byte nextImageIndex;
    private byte currentImageIndex;
    private byte prevImageIndex;
    private int[] imageLibrary;     //array to store image resource id's

    /**
     * @param mframe Object referring to ImageView object
     */
    public ImageHandler(ImageSwitcher mframe) {
        frame = mframe;
        currentImageIndex = 0;
        nextImageIndex = (byte) (currentImageIndex + 1);
        prevImageIndex = 0;
    }

    /**
     * Call this method to initialise imageLibrary array
     *
     * @param a array contains images resource id
     */
    public void setImageLibrary(int[] a) {
        imageLibrary = new int[a.length];
        System.arraycopy(a, 0, imageLibrary, 0, imageLibrary.length);
    }

    public void mNextImage() {
        if (nextImageIndex < imageLibrary.length) {
            frame.setImageResource(imageLibrary[nextImageIndex]);
            currentImageIndex = nextImageIndex;
            prevImageIndex = (byte) (currentImageIndex - 1);
            nextImageIndex = (byte) (currentImageIndex + 1);
        } else {
            prevImageIndex = currentImageIndex;
            currentImageIndex = 0;
            nextImageIndex = (byte) (currentImageIndex + 1);
            frame.setImageResource(imageLibrary[currentImageIndex]);
        }
    }

    public void mPrevImage() {
        if (prevImageIndex != -1) {
            frame.setImageResource(imageLibrary[prevImageIndex]);
            currentImageIndex = prevImageIndex;
            prevImageIndex = (byte) (currentImageIndex - 1);
            nextImageIndex = (byte) (currentImageIndex + 1);
        } else {
            prevImageIndex = (byte) (imageLibrary.length - 2);
            nextImageIndex = currentImageIndex;
            currentImageIndex = (byte) (prevImageIndex + 1);
            frame.setImageResource((imageLibrary[currentImageIndex]));
        }
    }
}
