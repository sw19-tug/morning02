package com.gallery.android.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    public static final int STORAGE_READ_REQUEST = 1;
    public static final int FULLSCREEN_REQUEST = 2;
    public static final int OPEN_ZIP_REQUEST = 3;
    public static final int ROTATE_REQUEST = 4;
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

            SharedPreferences sharedPref =
                    PreferenceManager.getDefaultSharedPreferences(this);

            /*Boolean switchPref = sharedPref.getBoolean
                    (SettingsActivity.KEY_PREF_TEST_SWITCH, true);

            Toast.makeText(this, switchPref.toString(), Toast.LENGTH_LONG).show();*/

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

        menu.findItem(R.id.search).setVisible(!selection_mode);
        menu.findItem(R.id.sort_name_asc).setVisible(!selection_mode);
        menu.findItem(R.id.sort_name_desc).setVisible(!selection_mode);
        menu.findItem(R.id.sort_date_asc).setVisible(!selection_mode);
        menu.findItem(R.id.sort_date_desc).setVisible(!selection_mode);
        menu.findItem(R.id.sort_size_asc).setVisible(!selection_mode);
        menu.findItem(R.id.sort_size_desc).setVisible(!selection_mode);
        menu.findItem(R.id.rotate_all).setVisible(selection_mode);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterImages adapterImages_sort = (AdapterImages) recyclerImages.getAdapter();
        switch(item.getItemId()) {
        case R.id.import_zip: {
            System.out.println("import zip pressed");
            performFileSearch();
            return (true);
        }
        case R.id.sort_name_asc: {
            adapterImages_sort.sortByName(AdapterImages.SortOrder.ASCENDING);
            refreshView();
            return (true);
        }
        case R.id.sort_name_desc: {
            adapterImages_sort.sortByName(AdapterImages.SortOrder.DESCENDING);
            refreshView();
            return (true);
        }
        case R.id.sort_date_asc: {
            adapterImages_sort.sortByDate(AdapterImages.SortOrder.ASCENDING);
            refreshView();
            return (true);
        }
        case R.id.sort_date_desc: {
            adapterImages_sort.sortByDate(AdapterImages.SortOrder.DESCENDING);
            refreshView();
            return (true);
        }
        case R.id.sort_size_asc: {
            adapterImages_sort.sortBySize(AdapterImages.SortOrder.ASCENDING);
            refreshView();
            return (true);
        }
        case R.id.sort_size_desc: {
            adapterImages_sort.sortBySize(AdapterImages.SortOrder.DESCENDING);
            refreshView();
            return true;
        }
        case R.id.search: {
            EditText search_bar = findViewById(R.id.search_bar);
            if (search_bar.getVisibility() == View.GONE)
                search_bar.setVisibility(View.VISIBLE);
            else
                search_bar.setVisibility(View.GONE);
            return true;
        }
        case R.id.settings: {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
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
            setSelectionMode(false);
        } else {
            super.onBackPressed();
        }
    }


    private void buildRecycler() {

        recyclerImages = findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();
        final ArrayList<ImageContainer> image_list = f.loadImageContainers(this);

        AdapterImages adapter = new AdapterImages(image_list);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println(position);
                if (selection_mode) {

                    if (selection_list.contains(image_list.get(position))) {
                        selection_list.remove(image_list.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
                    } else {
                        selection_list.add(image_list.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                    }

                    if (selection_list.isEmpty())
                        setSelectionMode(false);
                } else {
                    String image_path = image_list.get(position).getPath();
                    System.out.println(image_path);
                    startFullScreenActivity(position,image_path);
                }
            }
        });

        adapter.setOnItemLongClickListener(new AdapterImages.LongClickListener() {
            @Override
            public void onItemLongClick(int position, View v) {
                if (!selection_mode) {
                    selection_list.add(image_list.get(position));
                    setSelectionMode(true);
                    v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerImages.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FULLSCREEN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                int result = data.getIntExtra("deletePos", -1);
                if (result > -1) {
                    AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();
                    adapterImages.getListImages().remove(result);
                    adapterImages.notifyItemRemoved(result);
                    adapterImages.notifyItemRangeChanged(result, adapterImages.getListImages().size());
                }
                String nameresult = data.getStringExtra("newName");
                int renameindex = data.getIntExtra("indexRename",-1);
                if(nameresult != "" && renameindex > -1){
                    AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();

                    String oldPath = adapterImages.getListImages().get(renameindex).getPath();
                    String newPath =  oldPath.substring(0,oldPath.lastIndexOf("/")+1);
                    newPath = newPath + nameresult + oldPath.substring(oldPath.lastIndexOf("."));

                    if(!newPath.equals(oldPath)) {
                        while (existsName(newPath,oldPath))
                        {
                            nameresult = nameresult + "_copy";
                            newPath = newPath.substring(0,newPath.lastIndexOf("/")+1) + nameresult + newPath.substring(newPath.lastIndexOf("."));
                        }

                        File from = new File(oldPath);
                        File to = new File(newPath);
                        if (from.exists()) {
                            from.renameTo(to);
                            adapterImages.getListImages().get(renameindex).setFilename(nameresult);
                            adapterImages.getListImages().get(renameindex).setPath(newPath);
                        }
                    }
                }
                int rotateIndex = data.getIntExtra("indexRotate",-1);
                if(rotateIndex > -1){
                    AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();
                    Bitmap oldBitmap = BitmapFactory.decodeFile(adapterImages.getListImages().get(rotateIndex).getPath());
                    Bitmap oldThumbnailBitmap = adapterImages.getListImages().get(rotateIndex).getImage();
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);

                    Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0,
                            oldBitmap.getWidth(),oldBitmap.getHeight(), matrix, true);

                    Bitmap newThumbnailBitmap = Bitmap.createBitmap(oldThumbnailBitmap, 0, 0,
                            oldThumbnailBitmap.getWidth(),oldThumbnailBitmap.getHeight(), matrix, true);

                    adapterImages.getListImages().get(rotateIndex).setImage(newThumbnailBitmap);
                    adapterImages.notifyItemChanged(rotateIndex);
                    String path = adapterImages.getListImages().get(rotateIndex).getPath();

                    String extension = path.substring(path.lastIndexOf("."));
                    Bitmap.CompressFormat myFormat = Bitmap.CompressFormat.PNG;

                    switch (extension.toUpperCase()){
                        case "PNG":
                            myFormat = Bitmap.CompressFormat.PNG;
                            break;
                        case "JPEG":
                            myFormat = Bitmap.CompressFormat.JPEG;
                            break;
                        case "WEBP":
                            myFormat = Bitmap.CompressFormat.WEBP;
                            break;
                        default:
                            break;
                    }
                    OutputStream fOut = null;
                    File file = new File(path);
                    try {
                        fOut = new FileOutputStream(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    newBitmap.compress(myFormat, 100, fOut);
                    try {
                        fOut.flush();
                        fOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    startFullScreenActivity(rotateIndex, path);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }

        if(requestCode == OPEN_ZIP_REQUEST ){
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Uri path = data.getData();
                    if (!unzip(path, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()))
                    {
                        Toast.makeText(this, "Could not unzip.", Toast.LENGTH_LONG).show();
                        return;
                    }
                    buildRecycler();
                }
            }
        }
    }

    private void startFullScreenActivity(int pos, String path) {
        Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
        fullscreenImageIntent.putExtra("path", path);
        fullscreenImageIntent.putExtra("index", pos);
        startActivityForResult(fullscreenImageIntent,FULLSCREEN_REQUEST);
    }

    private boolean existsName(String newPath, String oldPath) {
        String dirPath = newPath.substring(0,newPath.lastIndexOf("/"));
        File dir = new File(dirPath);
        if(dir.exists() && dir.isDirectory())
        {
            File[] filelist = dir.listFiles();
            for (File f : filelist) {
                if(f.getPath().equals(newPath) && !f.getPath().equals(oldPath))
                    return true;
            }
        }
        return false;
    }

    private void onSearchClicked(AdapterImages adapter) {
        EditText searchbar_input = (EditText) findViewById(R.id.search_bar);

        String image_path = adapter.searchPictures(searchbar_input.getText().toString());

        if (image_path != null) {
            Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
            fullscreenImageIntent.putExtra("path", image_path);
            startActivity(fullscreenImageIntent);
        }
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
        intent.setType("application/zip");
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
            if (zis != null)
                try {
                    zis.close();
                } catch (IOException e) {
                    return false;
                }
        }
        return true;
    }

    public void refreshView()
    {
        ((AdapterImages)recyclerImages.getAdapter()).notifyDataSetChanged();
    }

    private void setSelectionMode(boolean selection_mode)
    {
        if (selection_mode)
        {
            EditText search_bar = findViewById(R.id.search_bar);
            if (search_bar.getVisibility() == View.VISIBLE)
                search_bar.setVisibility(View.GONE);
        }
        this.selection_mode = selection_mode;
        this.invalidateOptionsMenu();
    }

}