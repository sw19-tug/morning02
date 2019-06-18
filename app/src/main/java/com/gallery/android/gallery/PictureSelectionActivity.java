package com.gallery.android.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PictureSelectionActivity extends AppCompatActivity {

    public static String path;
    public String album_path;
    private boolean isNightModeEnabled = (Boolean) GalleryApplication.getInstance().get("nightMode");
    Switch nightmodeswitch;

    public List<ImageContainer> selection_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNightModeEnabled()) {
            setTheme(R.style.DarkTheme);
        }
        this.isNightModeEnabled= (Boolean) GalleryApplication.getInstance().get("nightMode");
        album_path = getIntent().getExtras().getString("path");
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();

        setContentView(R.layout.picture_selection);
        this.setTitle("Add pictures");

        buildRecycler();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                finish();
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

        RecyclerView recyclerImages = findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();
        final ArrayList<ImageContainer> image_list = f.loadImageContainersForPath(path, true, this);

        AdapterImages adapter = new AdapterImages(image_list);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println("Picture selection!!!!");
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

    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }
    public void setIsNightModeEnabled(boolean isNightModeEnabled) {
        this.isNightModeEnabled = isNightModeEnabled;
    }
}
