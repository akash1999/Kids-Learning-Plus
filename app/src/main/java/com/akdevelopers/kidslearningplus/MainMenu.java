package com.akdevelopers.kidslearningplus;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainMenu extends Activity {

    private MediaPlayer player;
    private boolean isBelowKitkat;
    private View decorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_bottom);
        setContentView(R.layout.activity_main_menu);


        isBelowKitkat = (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT);
    }

    @Override
    protected void onStart() {
        super.onStart();

        decorView = getWindow().getDecorView();
        player = MediaPlayer.create(this, R.raw.main_menu_background_music);
        player.setLooping(true);
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

        player.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();

        player.stop();
        player.release();
        player = null;
        decorView = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isBelowKitkat) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }

        return super.onTouchEvent(event);
    }

    @SuppressWarnings({"unused", "UnusedParameters"})
    public void launchAbcActivity(View view) {
        Intent abc_Activity = new Intent(this, abcActivity.class);
        startActivity(abc_Activity);
        finish();
    }

}
