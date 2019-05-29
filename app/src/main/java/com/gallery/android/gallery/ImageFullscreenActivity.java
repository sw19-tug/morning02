package com.gallery.android.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class    ImageFullscreenActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("path");
        DummyParceable actual_image = bundle.getParcelable("parceable");




        setContentView(R.layout.activity_image_fullscreen);
        ImageView image = findViewById(R.id.fullscreen_image_view);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);
        Button shareButton= (Button)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent shareContentActivity = new Intent(ImageFullscreenActivity.this, ShareContentActivity.class);
                startActivity(shareContentActivity);
            }
        });

        Button tagsButton = (Button)findViewById(R.id.tagsButton);

        tagsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent tagsContentActivity = new Intent(ImageFullscreenActivity.this, TagActivity.class);
                startActivity(tagsContentActivity);
            }
        });
    }
}
