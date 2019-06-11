package com.gallery.android.gallery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class ImageFullscreenActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    String path="";
    int index = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        path = getIntent().getExtras().getString("path");
        index = getIntent().getExtras().getInt("index");
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
            case R.id.deleteButton:
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
            case R.id.renameButton:
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
            case R.id.rotateButton:
                        int rotateIndex = index;
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("indexRotate",rotateIndex);
                        setResult(Activity.RESULT_OK,returnIntent);
                        ImageFullscreenActivity.this.finish();
                return true;
            default:
                return false;
        }
    }
}
