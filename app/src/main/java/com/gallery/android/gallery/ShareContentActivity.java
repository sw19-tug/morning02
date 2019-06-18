package com.gallery.android.gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShareContentActivity  extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType("*/*");
        Intent share=Intent.createChooser(intent,"Share Using");
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(share);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle extras = data.getExtras();

        Bitmap thePic = extras.getParcelable("data");
        savebitmap(thePic);
    }
    private String savebitmap(Bitmap bmp) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, "QR_" + timeStamp + ".png");

        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "QR_" + timeStamp + ".png");
        }

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
