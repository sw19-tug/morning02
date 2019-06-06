package com.gallery.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PictureSelectionActivity extends AppCompatActivity {

    public static RecyclerView recyclerImages;
    public static String path;
    public String album_path;

    public List<ImageContainer> selection_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        album_path = getIntent().getExtras().getString("path");
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        setContentView(R.layout.picture_selection);

        buildRecycler();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picture_selection, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.cancel: {
                onBackPressed();
                return (true);
            }
            case R.id.confirm: {
                for (ImageContainer image : selection_list)
                {
                    File source = new File(image.getPath());

                    String new_file_path = album_path + image.getPath().substring(image.getPath().lastIndexOf("/"));
                    File new_file = new File(new_file_path);
                    moveFile(source, new_file);

                }
                setResult(Activity.RESULT_OK);
                PictureSelectionActivity.this.finish();
                return (true);
            }
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }


    private void buildRecycler() {

        recyclerImages = findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();
        final ArrayList<ImageContainer> image_list = f.loadImageContainersForPath(path, true, this);

        AdapterImages adapter = new AdapterImages(image_list);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                if (selection_list.contains(image_list.get(position))) {
                    selection_list.remove(image_list.get(position));
                    v.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
                } else {
                    selection_list.add(image_list.get(position));
                    v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                }


            }
        });

        recyclerImages.setAdapter(adapter);
    }

    private boolean copyFile(File source, File dest){
        try{
            java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);

            try{
                java.io.FileOutputStream destinationFile = null;

                try{
                    destinationFile = new FileOutputStream(dest);

                    byte buffer[] = new byte[512 * 1024];
                    int read;

                    while ((read = sourceFile.read(buffer)) != -1){
                        destinationFile.write(buffer, 0, read);
                    }
                } finally {
                    destinationFile.close();
                }
            } finally {
                sourceFile.close();
            }
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean moveFile(File source,File destination) {

        boolean result = copyFile(source, destination);
        if (result) {
            result = source.delete();
        }
        return result;
    }
}
