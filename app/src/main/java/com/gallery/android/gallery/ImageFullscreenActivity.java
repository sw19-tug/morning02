package com.gallery.android.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class ImageFullscreenActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    String path="";
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        path = getIntent().getExtras().getString("path");
        setContentView(R.layout.activity_image_fullscreen);
        ImageView image = findViewById(R.id.fullscreen_image_view);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);


    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cropButton:
                Intent cropImageActivity = new Intent(ImageFullscreenActivity.this, CropImageActivity.class);
                cropImageActivity.putExtra("path", path);
                startActivity(cropImageActivity);
                return true;
            case R.id.shareButton:
                Intent shareContentActivity = new Intent(ImageFullscreenActivity.this, ShareContentActivity.class);
                startActivity(shareContentActivity);
                return true;
            case R.id.exportButton:
                String[] paths = {path};
                try {
                    ExportImages.compressImage(paths, path+"_export.zip");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;

            default:
                return false;
        }
    }
}
