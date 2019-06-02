package com.gallery.android.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.gallery.android.gallery.MainActivity.FULLSCREEN_REQUEST;

public class AddImageActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static String path;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);


    }
    public String getPath(){
        return path;
    }
    public void setPath(String nPath){
        path=nPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {


            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String name=savebitmap(photo);
            data.putExtra("namePhoto",name);
            Intent imageFullScreenIntent = new Intent(AddImageActivity.this, ImageFullscreenActivity.class);
            imageFullScreenIntent.putExtra("path", path);
            imageFullScreenIntent.putExtra("index", 3);
            startActivityForResult(imageFullScreenIntent,FULLSCREEN_REQUEST);
        }
    }
    public String savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, "QR_" + timeStamp + ".png");

        if (file.exists()) {
            file.delete();
            //file = new File(extStorageDirectory, "QR_" + timeStamp + ".png");
            file = new File(extStorageDirectory, "QR_" + timeStamp + ".png");
            setPath(file.getPath());

        }
        else{
            file = new File(extStorageDirectory, "QR_" + timeStamp + ".png");
            setPath(file.getPath());
        }
        setPath(file.getPath());
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return timeStamp;
    }
}
