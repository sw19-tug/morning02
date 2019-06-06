package com.gallery.android.gallery;

import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TagActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    int imageId = -1;

    String insert_text = "";
    public List<Tags> tags_;
    private List<String> checkedTags = new ArrayList<String>();
    public RecyclerView recyclerTags;
    private ImageContainer actual_image_container;

    public void TagsMenu(View view) {
        PopupMenu tags_menu = new PopupMenu(this, view);
        MenuInflater inflater = tags_menu.getMenuInflater();
        inflater.inflate(R.menu.tags_menu, tags_menu.getMenu());
        tags_menu.setOnMenuItemClickListener(this);
        tags_menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.apply_button_menu:
                for (int i = 0; i < tags_.size(); i++) {

                    Tags actual_tag = tags_.get(i);

                    String tag_name = actual_tag.getName();
                    int actual_tag_id = actual_tag.getTagId();
                    System.out.println("tag_id_+100=" + actual_tag_id + 100);

                    int tag_id = 100 + actual_tag_id;
                    int tag_id2 = 200 + actual_tag_id;
                    System.out.println("tag_id=" + tag_id);

                    CheckBox checkBox = (CheckBox) findViewById(tag_id);
                    TextView text_checkBox = (TextView) findViewById(tag_id2);

                    String tagName = (String) text_checkBox.getText();
                    System.out.println(checkBox.getId());

                    Boolean selected = checkBox.isChecked();
                    ArrayList<String> checked = new ArrayList<String>();

                    if (selected == true) {
                        checkBox.setChecked(true);
                        checked.add(tagName);
                        System.out.println("Checked" + checkBox.getId());
                    } else {
                        checkBox.setChecked(false);
                    }
                }
                return true;


            case R.id.item_tagsmenu_addtag:

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

                        AdapterTags adapter = (AdapterTags) recyclerTags.getAdapter();


                        insert_text = input.getText().toString();

                        for (int i = 0; i < adapter.getItemCount(); i++) {
                            if (adapter.tags_.get(i).getName().equals(insert_text))
                                return;
                        }

                        Tags new_tag = new Tags(insert_text);

                        ((GalleryApplication) getApplication()).tags.add(new_tag);


                        adapter.addItem(new_tag);


                        recyclerTags.getAdapter().notifyItemInserted(recyclerTags.getAdapter().getItemCount());

                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;


            case R.id.item_tagsmenu_selectall:

                RecyclerView res = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);

                for (int j = 0; j < tags_.size(); j++) {

                    if (!actual_image_container.tags.contains(tags_.get(j))) {

                        actual_image_container.tags.add(tags_.get(j));

                    }



/*
                    LinearLayout lin = (LinearLayout)res.findViewHolderForAdapterPosition(j).itemView;
                    CheckBox checkBox = (CheckBox) lin.findViewById(R.id.checkbox_tagitem_tick);

                    if (!checkBox.isChecked()) {
                        checkBox.callOnClick();
                        checkBox.setChecked(true);
                    }*/

                }
                res.getAdapter().notifyDataSetChanged();

                return true;


            case R.id.item_tagsmenu_unselectall:

                RecyclerView res1 = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);
                for (int j = 0; j < tags_.size(); j++) {
                    actual_image_container.tags.clear();
                }
                res1.getAdapter().notifyDataSetChanged();


        return true;
    }
        return false;

}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();

        try {

            imageId = bundle.getInt("index");
            actual_image_container = ((GalleryApplication) getApplication()).imgs.get(imageId);

            tags_ = ((GalleryApplication) getApplication()).tags;

        }
        catch (NullPointerException null_exception) {
            System.out.println("ImageContainer is faulty!");
            actual_image_container = new ImageContainer();

            ArrayList<Tags> tagsList = new ArrayList<Tags>();

            String basic_tags[] = {"T1", "T2", "T3", "T4", "T5"};

            for (int i = 0; i < basic_tags.length; i++) {
                tagsList.add(new Tags(basic_tags[i]));
            }


            tags_ = tagsList;
        }


        setContentView(R.layout.activity_tags);
        setUpRecyclerTags();

    }

    private void setUpRecyclerTags() {

        recyclerTags = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);
        final AdapterTags adapter = new AdapterTags(tags_,actual_image_container );


        adapter.setOnItemDeleteClickListener(new AdapterTags.ClickDeleteListener() {
            @Override
            public void onItemDeleteClick(int position, View v) {
                adapter.removeItem(position);
                ((GalleryApplication)getApplication()).tags.remove(position);

            }
        });

        adapter.setOnItemTickListener(new AdapterTags.ClickTickListener() {
            @Override
            public void onItemTick(int position, View v) {
                CheckBox c = (CheckBox)v;
                if (c.isChecked()) {
                    actual_image_container.tags.add(adapter.tags_.get(position));
                }
                else {
                    actual_image_container.tags.remove(adapter.tags_.get(position));
                }

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

                Tags new_tag = new Tags(insert_text);

                ((GalleryApplication)getApplication()).tags.add(new_tag);


                AdapterTags adapter = (AdapterTags)recyclerTags.getAdapter();
                adapter.addItem(new_tag);


                recyclerTags.getAdapter().notifyItemInserted(recyclerTags.getAdapter().getItemCount());
/*
                ;
                if(!a.hasItem(insert_text))
                    ad.addItem(insert_text);*/

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




}
