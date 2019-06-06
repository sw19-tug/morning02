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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PictureSelectionActivity extends AppCompatActivity {

    public static RecyclerView recyclerImages;
    public static String path;

    public List<ImageContainer> selection_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        setContentView(R.layout.picture_selection);

            buildRecycler();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
}
