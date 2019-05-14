package com.gallery.android.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class ImageFullscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        final String path = getIntent().getExtras().getString("path");
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

        Button export_button= (Button)findViewById(R.id.exportButton);
        export_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                String[] paths = {path};
                try {
                    ExportImages.compressImage(paths, path+"_export.zip");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
