package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        String path = getIntent().getExtras().getString("path");
        setContentView(R.layout.activity_image_fullscreen);
        ImageView image = findViewById(R.id.fullscreen_image_view);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);

    }
}
