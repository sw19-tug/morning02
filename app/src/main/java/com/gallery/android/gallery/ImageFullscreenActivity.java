package com.gallery.android.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

        Button deleteBtn = (Button)findViewById(R.id.delete_btn);
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //do something

                AlertDialog.Builder builder = new AlertDialog.Builder(ImageFullscreenActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        FileDeleter delete = new FileDeleter();
                        delete.delete(path);
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();alert.show();
            }
        });


    }
}
