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
import java.util.Collection;
import java.util.Collections;
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
    public List<Integer> selection_pos_list = new ArrayList<>();
    EditText editText;
    FileDeleter mainDeleter = new FileDeleter();

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
        menu.findItem(R.id.delete_all).setVisible(selection_mode);
        menu.findItem(R.id.share_all).setVisible(selection_mode);

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
        case R.id.rotate_all: {
            if (selection_list.size() == selection_pos_list.size()) {
                for (int index = 0; index < selection_pos_list.size(); index++) {
                    rotateImage((int) selection_pos_list.get(index));
                }
                resetSelectionMode();
            }
            selection_list.clear();
            selection_pos_list.clear();
            setSelectionMode(false);
            return true;
        }
            case R.id.delete_all: {
                if (selection_list.size() == selection_pos_list.size()) {
                    AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();
                    for (int index = 0; index < selection_pos_list.size(); index++) {
                        mainDeleter.delete(adapterImages.getListImages().get(index).getPath());
                    }
                    Collections.sort(selection_pos_list, Collections.<Integer>reverseOrder());
                    for (int index = 0; index < selection_pos_list.size(); index++) {
                        adapterImages.getListImages().remove((int)selection_pos_list.get(index));
                        adapterImages.notifyItemRemoved(selection_pos_list.get(index));
                        //adapterImages.notifyItemRangeChanged(selection_pos_list.get(index),adapterImages.getListImages().size());
                    }
                    adapterImages.notifyItemRangeChanged(0, adapterImages.getListImages().size());

                    selection_list.clear();
                    selection_pos_list.clear();
                    setSelectionMode(false);
                }
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


    private void rotateImage(int pos) {
        AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();
        Bitmap oldBitmap = BitmapFactory.decodeFile(adapterImages.getListImages().get(pos).getPath());
        Bitmap oldThumbnailBitmap = adapterImages.getListImages().get(pos).getImage();
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0,
                oldBitmap.getWidth(),oldBitmap.getHeight(), matrix, true);

        Bitmap newThumbnailBitmap = Bitmap.createBitmap(oldThumbnailBitmap, 0, 0,
                oldThumbnailBitmap.getWidth(),oldThumbnailBitmap.getHeight(), matrix, true);

        adapterImages.getListImages().get(pos).setImage(newThumbnailBitmap);
        adapterImages.notifyItemChanged(pos);
        String path = adapterImages.getListImages().get(pos).getPath();

        String extension = path.substring(path.lastIndexOf(".")+1);
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
    }

    @Override
    public void onBackPressed() {
        if (selection_mode) {
            resetSelectionMode();
        } else {
            super.onBackPressed();
        }
    }

    private void resetSelectionMode() {
        RecyclerView rec_view = (RecyclerView) this.findViewById(R.id.RecyclerId);

        for (int i = 0; i < rec_view.getChildCount(); i++) {
            RelativeLayout rel_layout = (RelativeLayout) rec_view.findViewHolderForAdapterPosition(i).itemView;
            rel_layout.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
        }

        selection_list.clear();
        selection_pos_list.clear();
        setSelectionMode(false);
    }


    private void buildRecycler() {
        recyclerImages = findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();

        ((GalleryApplication)getApplication()).imgs.clear();
        ((GalleryApplication)getApplication()).imgs.addAll(f.loadImageContainers(this));
        //final ArrayList<ImageContainer> image_list =
        //((GalleryApplication)this.getApplication()).imgs = imageList;

        AdapterImages adapter = new AdapterImages(((GalleryApplication)getApplication()).imgs);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println(position);
                if (selection_mode) {

                    if (selection_list.contains(((GalleryApplication)getApplication()).imgs.get(position))) {
                        selection_pos_list.remove((Integer)position);
                        selection_list.remove(((GalleryApplication)getApplication()).imgs.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
                    } else {
                        selection_list.add(((GalleryApplication)getApplication()).imgs.get(position));
                        selection_pos_list.add(position);
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                    }

                    if (selection_list.isEmpty())
                        setSelectionMode(false);
                } else {
                    String image_path = ((GalleryApplication)getApplication()).imgs.get(position).getPath();
                    System.out.println(image_path);
                    startFullScreenActivity(position,image_path);
                }
            }
        });

        adapter.setOnItemLongClickListener(new AdapterImages.LongClickListener() {
            @Override
            public void onItemLongClick(int position, View v) {
                if (!selection_mode) {
                    selection_list.add(((GalleryApplication)getApplication()).imgs.get(position));
                    selection_pos_list.add(position);
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
                    mainDeleter.delete(adapterImages.getListImages().get(result).getPath());
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
                    rotateImage(rotateIndex);
                    AdapterImages adapterImages = (AdapterImages) recyclerImages.getAdapter();
                    startFullScreenActivity(rotateIndex, adapterImages.getListImages().get(rotateIndex).getPath());
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
        String searchbar_input = ((EditText) findViewById(R.id.search_bar)).getText().toString();

        RecyclerView recyclerview_images = findViewById(R.id.RecyclerId);
        AdapterImages adapter_images = (AdapterImages)recyclerview_images.getAdapter();
        GalleryApplication application_gallery = (GalleryApplication)getApplication();
        List<ImageContainer> list_images =  new ArrayList<>();
        list_images.addAll(application_gallery.imgs);
        List<Tags> list_tags = application_gallery.tags;

        if (searchbar_input.equals(""))
            adapter_images.replaceItems(list_images);

        Tags search_tag = null;

        for(int i = 0; i < list_tags.size(); i++){
            if(searchbar_input.equals(list_tags.get(i).getName())){
                search_tag = list_tags.get(i);
                break;
            }
        }

        List<ImageContainer> list_images_tag_hit = new ArrayList<>();
        List<ImageContainer> list_images_name_hit = new ArrayList<>();

        for (ImageContainer image: list_images) {

            if ((search_tag != null) && (image.tags.contains(search_tag)))
                list_images_tag_hit.add(image);
            else if (image.getFilename().contains(searchbar_input))
                list_images_name_hit.add(image);

        }

        list_images_tag_hit.addAll(list_images_name_hit);
        adapter_images.replaceItems(list_images_tag_hit);

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