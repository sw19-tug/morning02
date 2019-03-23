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

        ImageView iv_1 = findViewById(R.id.small_image_view);
        ImageView iv_2 = findViewById(R.id.small_image_view_2);

        final int res_1 = R.drawable.duckduck;
        final int res_2 = R.drawable.duckduckdwo;

        iv_1.setImageResource(res_1);
        iv_2.setImageResource(res_2);

        iv_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                    fullscreenImageIntent.putExtra("resID", res_1);
                    startActivity(fullscreenImageIntent);
                }
        });

        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                fullscreenImageIntent.putExtra("resID", res_2);
                startActivity(fullscreenImageIntent);
            }
        });
    }
}
