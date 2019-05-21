package com.gallery.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    public static final int STORAGE_READ_REQUEST = 1;
    public static final int FULLSCREEN_REQUEST = 2;
    public static final int OPEN_ZIP_REQUEST = 3;
    private static final int BUFFER_SIZE = 8192 ;//2048;
    RecyclerView recyclerImages;

    public boolean selection_mode = false;
    public List<ImageContainer> selection_list = new ArrayList<>();
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_READ_REQUEST);

        } else {
            buildRecycler();
            setEditText();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case STORAGE_READ_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildRecycler();
                    setEditText();
                } else {
                    finish();
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.import_zip: {
                System.out.println("import zip pressed");
                // import the zip
                return (true);
            }
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        if (selection_mode) {
            RecyclerView rec_view = (RecyclerView) this.findViewById(R.id.RecyclerId);

            for (int i = 0; i < rec_view.getChildCount(); i++) {
                RelativeLayout rel_layout = (RelativeLayout) rec_view.findViewHolderForAdapterPosition(i).itemView;
                rel_layout.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
            }

            selection_list.clear();
            selection_mode = false;
        } else {
            super.onBackPressed();
        }
    }


    private void buildRecycler() {
        recyclerImages = findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();
        final ArrayList<ImageContainer> imageList = f.loadImageContainers();

        AdapterImages adapter = new AdapterImages(imageList);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println(position);
                if (selection_mode) {

                    if (selection_list.contains(imageList.get(position))) {
                        selection_list.remove(imageList.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
                    } else {
                        selection_list.add(imageList.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                    }

                    if (selection_list.isEmpty())
                        selection_mode = false;
                } else {
                    String image_path = imageList.get(position).getPath();
                    System.out.println(image_path);
                    Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                    fullscreenImageIntent.putExtra("path", image_path);
                    fullscreenImageIntent.putExtra("index", position);
                    startActivityForResult(fullscreenImageIntent,FULLSCREEN_REQUEST);

                }
            }
        });

        adapter.setOnItemLongClickListener(new AdapterImages.LongClickListener() {
            @Override
            public void onItemLongClick(int position, View v) {
                if (!selection_mode) {
                    selection_list.add(imageList.get(position));
                    selection_mode = true;
                    v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerImages.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FULLSCREEN_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                int result=data.getIntExtra("deletePos",-1);
                if(result > -1)
                {
                    AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();
                    adapterImages.getListImages().remove(result);
                    adapterImages.notifyItemRemoved(result);
                    adapterImages.notifyItemRangeChanged(result,adapterImages.getListImages().size());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
        if(requestCode == OPEN_ZIP_REQUEST){
            if(resultCode == Activity.RESULT_OK) {
                if (data != null) {

                   EditText searchbar_input = (EditText) findViewById(R.id.search_bar);
                   Uri path =  data.getData();
                   searchbar_input.setText(unzip(path,
                           Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()).toString());

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }
    }

    private void onSearchClicked(AdapterImages adapter) {
        EditText searchbar_input = (EditText) findViewById(R.id.search_bar);
       /* ImageContainer image = adapter.searchPictures(searchbar_input.getText().toString());

        if (image != null) {
            searchbar_input.setText("Found: " + image.getFilename());
        }
        */
       performFileSearch();
    }

    private void setEditText() {
        editText = (EditText) findViewById(R.id.search_bar);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    onSearchClicked((AdapterImages) recyclerImages.getAdapter());
                    return true;
                }
                return false;
            }
        });
    }

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/*");
        startActivityForResult(intent, OPEN_ZIP_REQUEST);


    }

    public Boolean unzip(Uri sourceFile, String destinationFolder)  {
        ZipInputStream zis = null;

        try {
            FileInputStream istream = (FileInputStream) this.getContentResolver().openInputStream(sourceFile);
            zis = new ZipInputStream(new BufferedInputStream(istream));
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                fileName = fileName.substring(fileName.indexOf("/") + 1);
                File file = new File(destinationFolder, fileName);
                File dir = ze.isDirectory() ? file : file.getParentFile();

                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Invalid path: " + dir.getAbsolutePath());
                if (ze.isDirectory()) continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }

            }
        } catch (IOException ioe){
            System.out.println(ioe.getStackTrace());
            return false;
        }  finally {
            if(zis!=null)
                try {
                    zis.close();
                } catch(IOException e) {

                }
        }
        return true;
    }

    }
