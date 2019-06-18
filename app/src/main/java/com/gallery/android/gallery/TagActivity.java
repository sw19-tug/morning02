package com.gallery.android.gallery;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class TagActivity extends AppCompatActivity  {

    int imageId = -1;

    String insert_text = "";
    public List<Tags> tags_;
    private List<String> checkedTags = new ArrayList<String>();
    public RecyclerView recyclerTags;
    private ImageContainer actual_image_container;
    private boolean isNightModeEnabled = (Boolean) GalleryApplication.getInstance().get("nightMode");
    Switch nightmodeswitch;

    private Menu action_menu;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tags, menu);
        action_menu = menu;

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_add:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("New tag");

                View viewInflated = LayoutInflater.from(this).inflate(R.layout.add_name_tags, (ViewGroup) findViewById(R.id.frame), false);

                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

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
                        ((GalleryApplication)getApplication()).updateTagPreferences();
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


            case R.id.menu_selectall:

                RecyclerView res = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);

                for (int j = 0; j < tags_.size(); j++) {

                    if (!actual_image_container.tags.contains(tags_.get(j))) {

                        actual_image_container.tags.add(tags_.get(j));
                    }

                }
                ((GalleryApplication)getApplication()).updateImageTagPreferance(
                        actual_image_container.getPath(),  actual_image_container.tags);
                res.getAdapter().notifyDataSetChanged();

                return true;

            case R.id.menu_deselectall:

                RecyclerView res1 = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);
                for (int j = 0; j < tags_.size(); j++) {
                    actual_image_container.tags.clear();
                }
                ((GalleryApplication)getApplication()).updateImageTagPreferance(
                        actual_image_container.getPath(),  actual_image_container.tags);
                res1.getAdapter().notifyDataSetChanged();

        return true;
    }
    return false;
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isNightModeEnabled()) {
            setTheme(R.style.DarkTheme);
        }
        this.isNightModeEnabled= (Boolean) GalleryApplication.getInstance().get("nightMode");
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
    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }
    private void setUpRecyclerTags() {

        recyclerTags = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);
        final AdapterTags adapter = new AdapterTags(tags_,actual_image_container );


        adapter.setOnItemDeleteClickListener(new AdapterTags.ClickDeleteListener() {
            @Override
            public void onItemDeleteClick(int position, View v) {
                adapter.removeItem(position);
                ((GalleryApplication)getApplication()).tags.remove(position);
                ((GalleryApplication)getApplication()).updateTagPreferences();
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
                ((GalleryApplication)getApplication()).updateImageTagPreferance(
                        actual_image_container.getPath(),  actual_image_container.tags);
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
