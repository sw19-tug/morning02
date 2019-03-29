package com.gallery.android.gallery;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageVo> listImages;
    RecyclerView recyclerImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        final List<ImageView> images = new ArrayList<>();
        images.add((ImageView) findViewById(R.id.small_image_view));
        images.add((ImageView) findViewById(R.id.small_image_view_2));

        for (final ImageView image : images) {
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String res = (String)image.getTag();
                    Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                    fullscreenImageIntent.putExtra("tag", res);
                    startActivity(fullscreenImageIntent);
                }
            });
        }
        */

        buildRecycler();



    }


    private void buildRecycler() {
        listImages=new ArrayList<>();
        recyclerImages= (RecyclerView) findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this,3));
        FileLoader f=new FileLoader();
        final ArrayList<ImageContainer> imageList=f.loadImageContainers();

        AdapterImages adapter=new AdapterImages(imageList);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                System.out.println(position);
                String image_path = imageList.get(position).getPath();
                Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                fullscreenImageIntent.putExtra("path", image_path);
                startActivity(fullscreenImageIntent);
            }
        });

        recyclerImages.setAdapter(adapter);


    }



    /* FileLoader loader = new FileLoader();
        List<String> paths = loader.getImagesPaths();
        List<ImageContainer> imageList = new ArrayList<ImageContainer>();
        for(int i = 0; i < paths.size(); i++ )
        {
            imageList.add(new ImageContainer(paths.get(i)));
        }

        ImageView firstIV = (ImageView) findViewById(R.id.image01);
        firstIV.setImageBitmap(imageList.get(0).getImage()); */

}
