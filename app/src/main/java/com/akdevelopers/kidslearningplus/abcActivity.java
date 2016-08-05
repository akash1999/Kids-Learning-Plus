package com.akdevelopers.kidslearningplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;


public class abcActivity extends Activity {
    private ImageHandler imageBundle;
    private SoundManager soundManager;
    private GestureDetectorCompat mDetector;
    private boolean isBelowKitkat;
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.fade_out);
        setContentView(R.layout.activity_abc_activity);


        initialise();
        isBelowKitkat = (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        soundManager = new SoundManager();
        loadAudio();

        decorView = getWindow().getDecorView();
        mDetector = new GestureDetectorCompat(abcActivity.this, new SwipeGesture());
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Setting immersive sticky flag for android kitkat and above
        if (!isBelowKitkat) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            //Make Navigation bar dim on api 18 and below
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }

        soundManager.initialiseIndex(imageBundle.getCurrentIndex());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDetector = null;
        decorView = null;
        soundManager.destroy();
        soundManager = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDetector != null) {
            this.mDetector.onTouchEvent(event);
        }

        if (isBelowKitkat) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
        return super.onTouchEvent(event);
    }

    @SuppressWarnings({"unused", "UnusedParameters"})
    public void launchMainMenu(View view) {
        imageBundle = null;
        Intent intent = new Intent(abcActivity.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(abcActivity.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    /**
     * To Initialise some things on new thread
     */
    private void initialise() {
        Thread load = new Thread(new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

                ImageSwitcher alphabet_switcher = (ImageSwitcher) findViewById(R.id.alphabet_switcher);

                alphabet_switcher.addView(new ImageView(abcActivity.this),
                        new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
                alphabet_switcher.addView(new ImageView(abcActivity.this),
                        new ImageSwitcher.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

                imageBundle = new ImageHandler(abcActivity.this, alphabet_switcher);

                int[] imagesId = {
                        R.drawable.alphabet_a, R.drawable.alphabet_b, R.drawable.alphabet_c, R.drawable.alphabet_d,
                        R.drawable.alphabet_e, R.drawable.alphabet_f, R.drawable.alphabet_g, R.drawable.alphabet_h,
                        R.drawable.alphabet_i, R.drawable.alphabet_j, R.drawable.alphabet_k, R.drawable.alphabet_l,
                        R.drawable.alphabet_m, R.drawable.alphabet_n, R.drawable.alphabet_o, R.drawable.alphabet_p,
                        R.drawable.alphabet_q, R.drawable.alphabet_r, R.drawable.alphabet_s, R.drawable.alphabet_t,
                        R.drawable.alphabet_u, R.drawable.alphabet_v, R.drawable.alphabet_w, R.drawable.alphabet_x,
                        R.drawable.alphabet_y, R.drawable.alphabet_z};

                imageBundle.setImageLibrary(imagesId);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageBundle.loadFirst();
                    }
                });
            }
        });
        load.start();
    }


    private void loadAudio() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                int[] ids = {
                        R.raw.voice_a, R.raw.voice_b, R.raw.voice_c, R.raw.voice_d, R.raw.voice_e,
                        R.raw.voice_f, R.raw.voice_g, R.raw.voice_h, R.raw.voice_i, R.raw.voice_j,
                        R.raw.voice_k, R.raw.voice_l, R.raw.voice_m, R.raw.voice_n, R.raw.voice_o,
                        R.raw.voice_p, R.raw.voice_q, R.raw.voice_r, R.raw.voice_s, R.raw.voice_t,
                        R.raw.voice_u, R.raw.voice_v, R.raw.voice_w, R.raw.voice_x, R.raw.voice_y,
                        R.raw.voice_z};
                soundManager.load(abcActivity.this, ids);
            }
        });
        t.start();
    }

    // Used to detect Gestures and changing images accordingly
    class SwipeGesture extends GestureDetector.SimpleOnGestureListener {
        private int counter = 0;

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float sensitivity = 100, eSensitivity = 300;

            if ((e2.getY() - e1.getY()) > eSensitivity) {
                if (counter == 4) {
                    Intent intent = new Intent(abcActivity.this, EasterEgg.class);
                    startActivity(intent);
                } else {
                    counter++;
                }
            }

            if ((e1.getX() - e2.getX()) > sensitivity) {                   //On swiping left
                imageBundle.mNextImage();
                soundManager.nextClip();
                counter = 0;
            } else if ((e2.getX() - e1.getX()) > sensitivity) {            //On swiping right
                imageBundle.mPrevImage();
                soundManager.prevClip();
                counter = 0;
            }
            return true;
        }
    }
}


