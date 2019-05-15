package com.gallery.android.gallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TagActivity extends AppCompatActivity {

    public List<Tags> tags_ = new ArrayList<Tags>();

    public RecyclerView recyclerTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tags);


        tags_.addAll(Tags.createTagsList(5));

    buildRecycler();






    }


    private void buildRecycler() {

        recyclerTags = findViewById(R.id.TagsRecyclerId);

        AdapterTags adapter = new AdapterTags(tags_);

        recyclerTags.setAdapter(adapter);

        recyclerTags.setLayoutManager(new LinearLayoutManager(this));



    }





}
