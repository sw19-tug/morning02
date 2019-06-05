package com.gallery.android.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
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

    String insert_text= "";
    public List<Tags> tags_ = new ArrayList<Tags>();
    private List<String> checkedTags=new ArrayList<String>();
    public RecyclerView recyclerTags;

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

                return true;


            case R.id.item_tagsmenu_selectall:

                RecyclerView res = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);

                for(int j=0; j< tags_.size();j++){

                    LinearLayout lin = (LinearLayout)res.findViewHolderForAdapterPosition(j).itemView;
                    CheckBox checkBox = (CheckBox) lin.findViewById(R.id.checkbox_tagitem_tick);
                    checkBox.setChecked(true);
                }

                return true;


            case R.id.item_tagsmenu_unselectall:
                RecyclerView res1 = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);

                for(int j=0; j< tags_.size();j++){

                    LinearLayout lin = (LinearLayout)res1.findViewHolderForAdapterPosition(j).itemView;
                    CheckBox checkBox = (CheckBox) lin.findViewById(R.id.checkbox_tagitem_tick);
                    checkBox.setChecked(false);
                }
                return true;
        }
        return false;
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

        recyclerTags = findViewById(R.id.recyclerview_tagsactivity_tagscontainer);
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


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }



}
