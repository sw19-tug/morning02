package com.gallery.android.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class AlbumOverviewActivity extends AppCompatActivity {

    public static RecyclerView recyclerAlbums;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_overview);
        buildRecycler();
    }

    private void buildRecycler(){
        recyclerAlbums = findViewById(R.id.albumRecyclerId);
        recyclerAlbums.setLayoutManager(new LinearLayoutManager(this));

        FileLoader f = new FileLoader();
        final ArrayList<String> albumList = f.loadAlbums();

        AdapterAlbums adapter = new AdapterAlbums(albumList);

        adapter.setOnItemClickListener(new AdapterAlbums.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                String albumPath = albumList.get(position);

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
                finish();
            }
        });

        recyclerAlbums.setAdapter(adapter);
    }
}

