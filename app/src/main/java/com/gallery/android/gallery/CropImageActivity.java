package com.gallery.android.gallery;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CropImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        String path = getIntent().getExtras().getString("path");
        performCrop(path);


    }

    protected void performCrop(String path){
        final int PIC_CROP = 2;

        try {

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String pathName=path.toString();
            File file = new File(path.toString());
            String filename = file.getName();
            System.out.println(filename);
            Uri uri = FileProvider.getUriForFile(CropImageActivity.this, BuildConfig.APPLICATION_ID + ".provider",new File(path));
            cropIntent.setDataAndType(uri, "image/  *");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("fileName",filename);
            cropIntent.putExtra("return-data", true);
            cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            setResult(Activity.RESULT_OK, cropIntent);
            SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.clear();

            editor.putString("filename",filename);

            editor.commit();
            startActivityForResult(cropIntent, PIC_CROP);

        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Device does not support cropping!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK) {
            this.finish();
        }
        else {
            Bitmap thePic = data.getExtras().getParcelable("data");
            SharedPreferences settings = getSharedPreferences("PREFS_NAME", MODE_PRIVATE);
            String filen = "h";
            String filename = settings.getString("filename", filen);
            savebitmap(thePic, filename);
            Intent mainIntent = new Intent(CropImageActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }

    }
    private String savebitmap(Bitmap bmp,String name) {
        String extStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        OutputStream outStream = null;
        // String temp = null;
        File file = new File(extStorageDirectory, timeStamp+"Crop" + name );

        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, timeStamp+"Crop" + name );


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
