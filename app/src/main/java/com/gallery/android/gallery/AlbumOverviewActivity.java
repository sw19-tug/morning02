package com.gallery.android.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class AlbumOverviewActivity extends AppCompatActivity {

    public static RecyclerView recyclerAlbums;

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


}

