package com.akdevelopers.kidslearningplus;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity {

    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    protected void onStart() {
        super.onStart();

        player = MediaPlayer.create(this, R.raw.main_menu_background_music);
        player.setLooping(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Setting immersive sticky flag for android kitkat and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
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
    }

    public void launchAbcActivity(View view) {
        Intent abc_Activity = new Intent(this, abcActivity.class);
        startActivity(abc_Activity);
    }

}
