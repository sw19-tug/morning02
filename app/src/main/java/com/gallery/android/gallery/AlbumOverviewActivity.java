package com.gallery.android.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import static com.gallery.android.gallery.MainActivity.FULLSCREEN_REQUEST;

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
                /*
                Intent fullscreenImageIntent = new Intent(MainActivity.this, AlbumMainActivity.class);
                fullscreenImageIntent.putExtra("path", albumPath);
                startActivityForResult(AlbumMainActivity,FULLSCREEN_REQUEST);
                */

                Intent main_activity_intent = new Intent(AlbumOverviewActivity.this, MainActivity.class);
                main_activity_intent.putExtra("path", albumPath);
                main_activity_intent.putExtra("include_subfolders", false);
                startActivity(main_activity_intent);

            }

    });


        recyclerAlbums.setAdapter(adapter);
    }
}

