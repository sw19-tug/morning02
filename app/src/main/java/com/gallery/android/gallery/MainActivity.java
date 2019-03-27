package com.gallery.android.gallery;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        final List<ImageView> images = new ArrayList<>();
        images.add((ImageView) findViewById(R.id.small_image_view));
        images.add((ImageView) findViewById(R.id.small_image_view_2));

        for (final ImageView image : images) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String res = (String)image.getTag();
                    Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                    fullscreenImageIntent.putExtra("tag", res);
                    startActivity(fullscreenImageIntent);
                }
            });
        }
        */
    }
}
