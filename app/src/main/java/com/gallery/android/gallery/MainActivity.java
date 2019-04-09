package com.gallery.android.gallery;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Adapter;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    ArrayList<ImageVo> listImages;
    RecyclerView recyclerImages;

    public boolean selection_mode = false;
    public List<ImageContainer> selection_list = new ArrayList<>();
    EditText editText;


    ArrayList<ImageContainer> imageList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildRecycler();

        editText = (EditText) findViewById(R.id.search_bar);
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                   onSearchClicked((AdapterImages)recyclerImages.getAdapter());

                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (selection_mode) {
            RecyclerView rec_view = (RecyclerView)this.findViewById(R.id.RecyclerId);


        buildRecycler();


            for (int i = 0; i < rec_view.getChildCount(); i++) {
                RelativeLayout rel_layout = (RelativeLayout)rec_view.findViewHolderForAdapterPosition(i).itemView;
                rel_layout.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
            }

            selection_list.clear();
            selection_mode = false;
        } else {
            super.onBackPressed();
        }
    }


    private void buildRecycler() {
        listImages=new ArrayList<>();
        recyclerImages= findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this,3));

        FileLoader f=new FileLoader();

        final ArrayList<ImageContainer> imageList=f.loadImageContainers();




        AdapterImages adapter=new AdapterImages(imageList);

        adapter.setOnItemClickListener(new AdapterImages.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                System.out.println(position);

                if (selection_mode) {

                    if (selection_list.contains(imageList.get(position))) {
                        selection_list.remove(imageList.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.GONE);
                    }
                    else {
                        selection_list.add(imageList.get(position));
                        v.findViewById(R.id.SelectedIcon).setVisibility(View.VISIBLE);
                    }


                    if (selection_list.isEmpty())
                        selection_mode = false;
                }
                else {
                    String image_path = imageList.get(position).getPath();
                    System.out.println(image_path);
                    Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
                    fullscreenImageIntent.putExtra("path", image_path);
                    startActivity(fullscreenImageIntent);
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

    private void onSearchClicked(AdapterImages adapter) {
        EditText searchbar_input = (EditText)findViewById(R.id.search_bar);
        System.out.println(searchbar_input.getText().toString());
        ImageContainer image = adapter.searchPictures(searchbar_input.getText().toString());

        if(image != null) {
            //Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
            //fullscreenImageIntent.putExtra("path", image.getPath());
            //startActivity(fullscreenImageIntent);

            searchbar_input.setText("Found: " + image.getFilename());
        }
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
