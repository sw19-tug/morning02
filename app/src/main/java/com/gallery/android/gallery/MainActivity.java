package com.gallery.android.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Adapter;

import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageVo> listImages;
    RecyclerView recyclerImages;
    EditText editText;

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


    private void buildRecycler() {
        listImages=new ArrayList<>();
        recyclerImages= (RecyclerView) findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this,3));
        FileLoader f=new FileLoader();
        ArrayList<ImageContainer> imageList=f.loadImageContainers();

        AdapterImages adapter=new AdapterImages(imageList);

        recyclerImages.setAdapter(adapter);


    }

    private void onSearchClicked(AdapterImages adapter) {
        EditText searchbar_input = (EditText)findViewById(R.id.search_bar);
        System.out.println(searchbar_input.getText().toString());
        ImageContainer image = adapter.searchPictures(searchbar_input.getText().toString());

        //Intent fullscreenImageIntent = new Intent(MainActivity.this, ImageFullscreenActivity.class);
        //fullscreenImageIntent.putExtra("path", image.getPath());
        //startActivity(fullscreenImageIntent);

        searchbar_input.setText("Found: " + image.getFilename());

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
