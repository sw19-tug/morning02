package com.gallery.android.gallery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.IOException;


public class ImageFullscreenActivity extends AppCompatActivity {
    String path="";
    int index = -1;
    private Menu action_menu;

    private boolean isNightModeEnabled = (Boolean) GalleryApplication.getInstance().get("nightMode");
    Switch nightmodeswitch;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fullscreen, menu);
        action_menu = menu;

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        if (isNightModeEnabled()) {
            setTheme(R.style.DarkTheme);
        }
        this.isNightModeEnabled= (Boolean) GalleryApplication.getInstance().get("nightMode");
        path = getIntent().getExtras().getString("path");
        index = getIntent().getExtras().getInt("index");
        setContentView(R.layout.activity_image_fullscreen);
        ImageView image = findViewById(R.id.fullscreen_image_view);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        image.setImageBitmap(bitmap);
    }
    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_crop:
                Intent cropImageActivity = new Intent(ImageFullscreenActivity.this, CropImageActivity.class);
                cropImageActivity.putExtra("path", path);
                startActivity(cropImageActivity);
                return true;
            case R.id.menu_share:
                Intent shareContentActivity = new Intent(ImageFullscreenActivity.this, ShareContentActivity.class);
                startActivity(shareContentActivity);
                return true;
            case R.id.menu_exprot:
                String[] paths = {path};
                try {
                    ExportImages.compressImage(paths, path+"_export.zip");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(ImageFullscreenActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        int deletePos = index;
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("deletePos",deletePos);
                        setResult(Activity.RESULT_OK,returnIntent);
                        ImageFullscreenActivity.this.finish();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;
            case R.id.menu_rename:
                AlertDialog.Builder inputTextBuilder = new AlertDialog.Builder(ImageFullscreenActivity.this);
                inputTextBuilder.setTitle("New Name: ");
                final EditText input = new EditText(this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                inputTextBuilder.setView(input);

                inputTextBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String newName = input.getText().toString();
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("indexRename",index);
                        returnIntent.putExtra("newName",newName);
                        setResult(Activity.RESULT_OK,returnIntent);
                        ImageFullscreenActivity.this.finish();
                        dialog.dismiss();
                    }
                });

                inputTextBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog inputTextDialog = inputTextBuilder.create();
                inputTextDialog.show();
                return true;
            case R.id.menu_rotate:
                        int rotateIndex = index;
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("indexRotate",rotateIndex);
                        setResult(Activity.RESULT_OK,returnIntent);
                        ImageFullscreenActivity.this.finish();
                return true;
            case R.id.menu_tags:

                Intent tagsContentActivity = new Intent(ImageFullscreenActivity.this, TagActivity.class);
                tagsContentActivity.putExtra("index", index);
                startActivity(tagsContentActivity);

                return  true;

            default:
                return false;
        }
    }
}
