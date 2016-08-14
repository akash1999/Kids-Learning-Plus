package com.akdevelopers.kidslearning;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Credits extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        //Setting Custom Font
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/njnaruto.ttf");
        TextView text[] = {(TextView) findViewById(R.id.credits_heading),
                (TextView) findViewById(R.id.credits_music),
                (TextView) findViewById(R.id.credits_app_design_code),
                (TextView) findViewById(R.id.credits_graphics)};

        for (int i = 0; i < text.length; i++) {
            text[i].setTypeface(font);
        }
    }
}
