package com.gallery.android.gallery;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AlbumOverviewActivity extends AppCompatActivity {

    public static final int SELECTION_REQUEST = 4;

    RecyclerView recyclerAlbums;
    private String album_name = "";
    private String album_path;
    private boolean isNightModeEnabled = (Boolean) GalleryApplication.getInstance().get("nightMode");
    Switch nightmodeswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (isNightModeEnabled()) {
            setTheme(R.style.DarkTheme);
        }
        this.isNightModeEnabled= (Boolean) GalleryApplication.getInstance().get("nightMode");
        setContentView(R.layout.activity_album_overview);
        this.setTitle("Albums");
        buildRecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_album_overview, menu);
        return true;
    }

    private void buildRecycler(){
        recyclerAlbums = findViewById(R.id.albumRecyclerId);
        recyclerAlbums.setLayoutManager(new GridLayoutManager(this, 3));

        FileLoader f = new FileLoader();
        final ArrayList<Pair<String, Bitmap>> albumList = f.loadAlbums();

        AdapterAlbums adapter = new AdapterAlbums(albumList);

        adapter.setOnItemClickListener(new AdapterAlbums.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                String albumPath = albumList.get(position).first;

                Intent returnIntent = new Intent();
                returnIntent.putExtra("title", albumPath.substring(albumPath.lastIndexOf("/") + 1));
                returnIntent.putExtra("path", albumPath);
                returnIntent.putExtra("album_mode", true);
                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
        });

        recyclerAlbums.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("title", "Gallery");
            returnIntent.putExtra("path", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
            returnIntent.putExtra("album_mode", false);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
    }

    private String createDirectory(String directory_name) {
        String full_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/" + directory_name;
        File dir = new File(full_path);

        if(!dir.exists() && dir.mkdirs()) {
            return full_path;
        }
        return null;
    }

    private void enterAlbumName()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle("Add album");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                album_name = input.getText().toString();

                album_path = createDirectory(album_name);
                if (album_path != null)
                {
                    Intent SelectionIntent = new Intent(AlbumOverviewActivity.this, PictureSelectionActivity.class);
                    SelectionIntent.putExtra("path", album_path);
                    startActivityForResult(SelectionIntent, SELECTION_REQUEST);
                    System.out.println("Created directory " + album_name);
                }
                else {
                    Toast.makeText(AlbumOverviewActivity.this, "Album creation failed", Toast.LENGTH_LONG).show();
                    System.out.println("Album creation failed.");
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AlbumOverviewActivity.this, "Cancelled", Toast.LENGTH_LONG);
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_album: {
                enterAlbumName();
                return (true);
            }
        }
        return(super.onOptionsItemSelected(item));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECTION_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                buildRecycler();
            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                new File(album_path).delete();
            }
        }
    }
    public boolean isNightModeEnabled() {
        return isNightModeEnabled;
    }
    public void setIsNightModeEnabled(boolean isNightModeEnabled) {
        this.isNightModeEnabled = isNightModeEnabled;
    }
}

