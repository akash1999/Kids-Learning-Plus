package com.akdevelopers.kidslearning.activityAlphabets;

import android.app.Activity;
import android.os.Bundle;

import com.akdevelopers.kidslearning.R;

public class EasterEgg extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
