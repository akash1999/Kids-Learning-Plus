package com.akdevelopers.kidslearning.activityAlphabets;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Process;

import com.akdevelopers.kidslearning.R;

import java.io.IOException;

public class SoundManager {

    private MediaPlayer player;
    private byte nextClipIndex;
    private byte currentClipIndex;
    private byte prevClipIndex;
    private AssetFileDescriptor[] aList;        //Array to hold FileDescriptors of all sound files
    private Context context;                    //Required to get AssetFileDescriptor

    public SoundManager() {
        player = new MediaPlayer();
    }

    public void load(Context c, int[] ids) {
        context = c;
        aList = new AssetFileDescriptor[ids.length];
        initialise();
    }

    public void initialiseIndex(byte currentIndex) {
        currentClipIndex = currentIndex;
        nextClipIndex = (byte) (currentClipIndex + 1);
        prevClipIndex = (byte) (currentClipIndex - 1);
    }

    /**
     * Initialise AssetFileDescriptor(aList) array
     */
    private void initialise() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                try {
                    aList[0] = context.getResources().openRawResourceFd(R.raw.voice_a);
                    aList[1] = context.getResources().openRawResourceFd(R.raw.voice_b);
                    aList[2] = context.getResources().openRawResourceFd(R.raw.voice_c);
                    aList[3] = context.getResources().openRawResourceFd(R.raw.voice_d);
                    aList[4] = context.getResources().openRawResourceFd(R.raw.voice_e);
                    aList[5] = context.getResources().openRawResourceFd(R.raw.voice_f);
                    aList[6] = context.getResources().openRawResourceFd(R.raw.voice_g);
                    aList[7] = context.getResources().openRawResourceFd(R.raw.voice_h);
                    aList[8] = context.getResources().openRawResourceFd(R.raw.voice_i);
                    aList[9] = context.getResources().openRawResourceFd(R.raw.voice_j);
                    aList[10] = context.getResources().openRawResourceFd(R.raw.voice_k);
                    aList[11] = context.getResources().openRawResourceFd(R.raw.voice_l);
                    aList[12] = context.getResources().openRawResourceFd(R.raw.voice_m);
                    aList[13] = context.getResources().openRawResourceFd(R.raw.voice_n);
                    aList[14] = context.getResources().openRawResourceFd(R.raw.voice_o);
                    aList[15] = context.getResources().openRawResourceFd(R.raw.voice_p);
                    aList[16] = context.getResources().openRawResourceFd(R.raw.voice_q);
                    aList[17] = context.getResources().openRawResourceFd(R.raw.voice_r);
                    aList[18] = context.getResources().openRawResourceFd(R.raw.voice_s);
                    aList[19] = context.getResources().openRawResourceFd(R.raw.voice_t);
                    aList[20] = context.getResources().openRawResourceFd(R.raw.voice_u);
                    aList[21] = context.getResources().openRawResourceFd(R.raw.voice_v);
                    aList[22] = context.getResources().openRawResourceFd(R.raw.voice_w);
                    aList[23] = context.getResources().openRawResourceFd(R.raw.voice_x);
                    aList[24] = context.getResources().openRawResourceFd(R.raw.voice_y);
                    aList[25] = context.getResources().openRawResourceFd(R.raw.voice_z);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                try {
                    player.setDataSource(aList[currentClipIndex].getFileDescriptor(), aList[currentClipIndex].getStartOffset(),
                            aList[currentClipIndex].getLength());
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * play next sound clip
     */
    public void nextClip() {
        //  Executed when not at end of queue
        if (nextClipIndex < aList.length) {

            try {
                player.reset();
                player.setDataSource(aList[nextClipIndex].getFileDescriptor(), aList[nextClipIndex].getStartOffset(),
                        aList[nextClipIndex].getLength());
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            currentClipIndex = nextClipIndex;
            prevClipIndex = (byte) (currentClipIndex - 1);
            nextClipIndex = (byte) (currentClipIndex + 1);

        } else {        //When at end of queue

            prevClipIndex = currentClipIndex;
            currentClipIndex = 0;
            nextClipIndex = (byte) (currentClipIndex + 1);

            try {
                player.reset();
                player.setDataSource(aList[currentClipIndex].getFileDescriptor(), aList[currentClipIndex].getStartOffset(),
                        aList[currentClipIndex].getLength());
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * play previous sound clip
     */
    public void prevClip() {
        if (prevClipIndex != -1) {//Check whether it is first member

            try {
                player.reset();
                player.setDataSource(aList[prevClipIndex].getFileDescriptor(), aList[prevClipIndex].getStartOffset(),
                        aList[prevClipIndex].getLength());
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

            currentClipIndex = prevClipIndex;
            prevClipIndex = (byte) (currentClipIndex - 1);
            nextClipIndex = (byte) (currentClipIndex + 1);

        } else {

            prevClipIndex = (byte) (aList.length - 2);
            nextClipIndex = currentClipIndex;
            currentClipIndex = (byte) (prevClipIndex + 1);

            try {
                player.reset();
                player.setDataSource(aList[currentClipIndex].getFileDescriptor(), aList[currentClipIndex].getStartOffset(),
                        aList[currentClipIndex].getLength());
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * release resources
     */
    public void destroy() {
        player.stop();
        player.release();
        player = null;
        aList = null;
        context = null;
    }
}
