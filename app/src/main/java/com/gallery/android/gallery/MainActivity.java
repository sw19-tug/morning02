package com.gallery.android.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerImages;

    public boolean selection_mode = false;
    public List<ImageContainer> selection_list = new ArrayList<>();
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

    @Override
    public void onBackPressed() {
        if (selection_mode) {
            RecyclerView rec_view = (RecyclerView)this.findViewById(R.id.RecyclerId);

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
            searchbar_input.setText("Found: " + image.getFilename());
        }
    }
}
