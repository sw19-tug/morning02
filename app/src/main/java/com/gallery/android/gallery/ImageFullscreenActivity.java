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

    private int imageId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String path = bundle.getString("path");
        int actual_image = bundle.getInt("imageId");
        System.out.println("ImageId: "+actual_image);
        imageId = actual_image;




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
                tagsContentActivity.putExtra("imageId", imageId);

                startActivity(tagsContentActivity);
            }
        });
    }
}
