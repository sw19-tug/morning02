package com.gallery.android.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class TagActivity extends AppCompatActivity {

    String insert_text= "";
    public List<Tags> tags_ = new ArrayList<Tags>();

    public RecyclerView recyclerTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tags);

        tags_.addAll(Tags.createTagsList());
    buildRecycler();

    }

    private void buildRecycler() {

        recyclerTags = findViewById(R.id.TagsRecyclerId);
        AdapterTags adapter = new AdapterTags(tags_);
        recyclerTags.setAdapter(adapter);
        recyclerTags.setLayoutManager(new LinearLayoutManager(this));
    }

    public void click_add_tag(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Title");

        View viewInflated = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_tags, (ViewGroup) findViewById(R.id.frame), false);

        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                insert_text = input.getText().toString();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }





}
