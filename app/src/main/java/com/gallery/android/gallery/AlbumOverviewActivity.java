package com.gallery.android.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AlbumOverviewActivity extends AppCompatActivity {

    public static RecyclerView recyclerAlbums;
    private String album_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_overview);
        buildRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_album_overview, menu);


        return true;
    }

    private void buildRecycler(){
        recyclerAlbums = findViewById(R.id.albumRecyclerId);
        recyclerAlbums.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();
        final ArrayList<Pair<String, Bitmap>> albumList = f.loadAlbums();

        AdapterAlbums adapter = new AdapterAlbums(albumList);

        adapter.setOnItemClickListener(new AdapterAlbums.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                String albumPath = albumList.get(position).first;

                FileLoader f = new FileLoader();
                final ArrayList<ImageContainer> image_list = f.loadImageContainersForPath(albumPath, false, AlbumOverviewActivity.this);
                AdapterImages adapter = (AdapterImages) MainActivity.recyclerImages.getAdapter();

                int i = 0;
                while (i < adapter.getListImages().size()) {
                    boolean found = false;
                    for (ImageContainer album_image : image_list)   {
                        if (adapter.getListImages().get(i).getPath().equals(album_image.getPath())) {
                            found = true;
                        }
                    }
                    if (!found) {
                        adapter.getListImages().remove(i);
                        adapter.notifyItemRemoved(i);
                        adapter.notifyItemRangeChanged(i, adapter.getListImages().size());
                    } else {
                        i++;
                    }
                }

                for (ImageContainer album_image : image_list)
                {
                    boolean found = false;
                    for (ImageContainer image : adapter.getListImages()) {
                        if (image.getPath().equals(album_image.getPath())) {
                            found = true;
                        }
                    }
                    if (!found) {
                        adapter.getListImages().add(album_image);
                        adapter.notifyItemInserted(adapter.getListImages().size() - 1);
                        adapter.notifyItemRangeChanged(adapter.getListImages().size() - 1, adapter.getListImages().size());
                    }
                }

                MainActivity.path = albumPath;
                MainActivity.album_mode = true;
                finish();
            }
        });

        recyclerAlbums.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (MainActivity.album_mode) {
            FileLoader f = new FileLoader();
            final ArrayList<ImageContainer> image_list = f.loadImageContainersForPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString(), true, AlbumOverviewActivity.this);
            AdapterImages adapter = (AdapterImages) MainActivity.recyclerImages.getAdapter();

            for (ImageContainer album_image : image_list)
            {
                boolean found = false;
                for (ImageContainer image : adapter.getListImages()) {
                    if (image.getPath().equals(album_image.getPath())) {
                        found = true;
                    }
                }
                if (!found) {
                    adapter.getListImages().add(album_image);
                    adapter.notifyItemInserted(adapter.getListImages().size() - 1);
                    adapter.notifyItemRangeChanged(adapter.getListImages().size() - 1, adapter.getListImages().size());
                }
            }

            MainActivity.path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();;
            MainActivity.album_mode = false;
            finish();
        } else {
            super.onBackPressed();
        }
    }

    private String createDirectory(String directory_name) {
        String full_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + directory_name;
        File dir = new File(full_path);

        if(!dir.exists() && dir.mkdirs()) {
            return full_path;
        }
        return null;
    }

    private boolean enterAlbumName()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add album");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                album_name = input.getText().toString();

                String folder = createDirectory(album_name);
                if (folder != null)
                {
                    Intent SelectionIntent = new Intent(AlbumOverviewActivity.this, PictureSelectionActivity.class);
                    SelectionIntent.putExtra("path", folder);
                    startActivity(SelectionIntent);
                    System.out.println("Created directory " + album_name);
                }
                else {
                    System.out.println("Album creation failed.");
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlbumOverviewActivity.this, "Cancelled", Toast.LENGTH_LONG);
                dialog.cancel();
            }
        });

        builder.show();
        return !album_name.isEmpty();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_album: {
                enterAlbumName();
                return (true);
            }
        }
        return(super.onOptionsItemSelected(item));
    }
}

