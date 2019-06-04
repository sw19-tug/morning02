package com.gallery.android.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import static com.gallery.android.gallery.R.id.tagsMenu;
import static com.gallery.android.gallery.R.menu.*;
import static com.gallery.android.gallery.R.menu.tags_menu;


public class TagActivity extends AppCompatActivity {

    String insert_text= "";
    public List<Tags> tags_ = new ArrayList<Tags>();
    private List<String> checkedTags=new ArrayList<String>();
    public RecyclerView recyclerTags;

    public void TagsMenu(View view) {
        PopupMenu tags_menu = new PopupMenu(this, view);
        MenuInflater inflater = tags_menu.getMenuInflater();
        //tags_menu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
        inflater.inflate(R.menu.tags_menu, tags_menu.getMenu());
        tags_menu.show();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tags);

        tags_.addAll(Tags.createTagsList());
        buildRecycler();
        System.out.println("aqui empieza");
        //RecyclerView r= findViewById();



    }

    private void buildRecycler() {

        recyclerTags = findViewById(R.id.TagsRecyclerId);
        final AdapterTags adapter = new AdapterTags(tags_);


        adapter.setOnItemClickListener(new AdapterTags.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                adapter.removeItem(position);

            }
        });

        recyclerTags.setAdapter(adapter);
        recyclerTags.setLayoutManager(new LinearLayoutManager(this));
    }

    public void click_add_tag(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New tag");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_name_tags, (ViewGroup) findViewById(R.id.frame), false);

        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);

// Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                insert_text = input.getText().toString();

                AdapterTags ad = (AdapterTags)recyclerTags.getAdapter();

                AdapterTags a = (AdapterTags)recyclerTags.getAdapter();
                if(!a.hasItem(insert_text))
                    ad.addItem(insert_text);

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void click_apply(View view) {


        for(int i=0; i< tags_.size();i++){

            Tags actual_tag = tags_.get(i);

            String tag_name = actual_tag.getName();
            int actual_tag_id = actual_tag.getTagId();
            System.out.println("tag_id_+100="+actual_tag_id+100);

            int tag_id=100+actual_tag_id;
            int tag_id2=200+actual_tag_id;
            System.out.println("tag_id="+tag_id);

            CheckBox checkBox = (CheckBox) findViewById(tag_id);
            TextView text_checkBox=(TextView) findViewById(tag_id2);

            String tagName=(String)text_checkBox.getText();
            System.out.println(checkBox.getId());

            Boolean selected= checkBox.isChecked();
            ArrayList<String> checked=new ArrayList<String>();

            if(selected == true){
                checkBox.setChecked(true);
                checked.add(tagName);
                System.out.println("Checked"+checkBox.getId());
            }
            else{
                checkBox.setChecked(false);
            }

            //GAL-015 imagecontainer
        }

    }


/* void click_select_all_button (View view)  {

        for(int j=0; j< tags_.size();j++){

            Tags actual_tag = tags_.get(j);

            String tag_name = actual_tag.getName();
            int actual_tag_id = actual_tag.getTagId();
            System.out.println("SELECT ALL tag_id_+100="+actual_tag_id+100);

            int tag_id=100+actual_tag_id;
            int tag_id2=200+actual_tag_id;
            System.out.println("tag_id="+tag_id);

            CheckBox checkBox = (CheckBox) findViewById(tag_id);
            TextView text_checkBox=(TextView) findViewById(tag_id2);

            String tagName=(String)text_checkBox.getText();
            System.out.println("CHECK SELECT ALL" +checkBox.getId());



            if(checkBox.isChecked()){
                System.out.println(checkBox.getText() + " is selected. \n");
                //checkBox.setChecked(true);
            }
            else{
                System.out.println(checkBox.getText() + " is not selected. \n");
                //checkBox.setChecked(true);
            }
        }
    }*/





}