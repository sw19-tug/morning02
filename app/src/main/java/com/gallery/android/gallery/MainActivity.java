package com.gallery.android.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageVo> listImages;
    RecyclerView recyclerImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildRecycler();


    }

    private void fillImages() {
        listImages.add(new ImageVo(R.drawable.image01));
        listImages.add(new ImageVo(R.drawable.image02));
        listImages.add(new ImageVo(R.drawable.image03));
        listImages.add(new ImageVo(R.drawable.image04));
        listImages.add(new ImageVo(R.drawable.image05));
        listImages.add(new ImageVo(R.drawable.image06));
        listImages.add(new ImageVo(R.drawable.image07));
        listImages.add(new ImageVo(R.drawable.image08));
        listImages.add(new ImageVo(R.drawable.image09));
        listImages.add(new ImageVo(R.drawable.image11));
        listImages.add(new ImageVo(R.drawable.image12));
        listImages.add(new ImageVo(R.drawable.image13));
        listImages.add(new ImageVo(R.drawable.image14));
        listImages.add(new ImageVo(R.drawable.image15));
        listImages.add(new ImageVo(R.drawable.image16));
        listImages.add(new ImageVo(R.drawable.image17));
        listImages.add(new ImageVo(R.drawable.image18));
        listImages.add(new ImageVo(R.drawable.image19));
        listImages.add(new ImageVo(R.drawable.image20));
        listImages.add(new ImageVo(R.drawable.image21));
        listImages.add(new ImageVo(R.drawable.image22));
        listImages.add(new ImageVo(R.drawable.image23));
        listImages.add(new ImageVo(R.drawable.image24));
        listImages.add(new ImageVo(R.drawable.image25));

    }

    private void buildRecycler() {
        listImages=new ArrayList<>();
        recyclerImages= (RecyclerView) findViewById(R.id.RecyclerId);
        recyclerImages.setLayoutManager(new GridLayoutManager(this,3));
        fillImages();
        AdapterImages adapter=new AdapterImages(listImages);

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
