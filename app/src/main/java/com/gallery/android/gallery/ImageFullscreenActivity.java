package com.gallery.android.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        int res = getIntent().getExtras().getInt("resID");
        setContentView(R.layout.activity_image_fullscreen);
        ImageView iv = findViewById(R.id.fullscreen_image_view);
        iv.setImageResource(res);

    }
}
