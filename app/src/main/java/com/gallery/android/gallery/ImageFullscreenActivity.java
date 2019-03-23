package com.gallery.android.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        String res = getIntent().getExtras().getString("tag");
        setContentView(R.layout.activity_image_fullscreen);
        ImageView image = findViewById(R.id.fullscreen_image_view);
        image.setImageResource(getResources().getIdentifier(res,"drawable",this.getPackageName()));




    }
}
