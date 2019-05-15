package com.gallery.android.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class TagActivity extends AppCompatActivity {

    static public Map<Integer, String> tags = new HashMap<>();

    public RecyclerView recyclerTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tags);

        //(TL)for testing purposes only. remove later.
        tags.put(1, "beautiful");
        tags.put(2, "XXX");

        //end remove.

    buildRecycler();






    }


    private void buildRecycler() {

        recyclerTags = findViewById(R.id.TagsRecyclerId);

        AdapterTags adapter = new AdapterTags(tags);



    }





}
