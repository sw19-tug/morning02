package com.gallery.android.gallery;

import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAlbums<getPath> extends RecyclerView.Adapter<AdapterAlbums.ViewHolderAlbums>  {
    private final ArrayList<Pair<String, Bitmap>> listAlbums;
    private static AdapterAlbums.ClickListener listener;

    public AdapterAlbums(ArrayList<Pair<String, Bitmap>> listAlbums) {
        this.listAlbums = listAlbums;
    }


    @Override
    @NonNull
    public AdapterAlbums.ViewHolderAlbums onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        int layout;
        layout = R.layout.album_view;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,null,false);
        return new AdapterAlbums.ViewHolderAlbums(view);
    }

    public int getItemCount() {
        return listAlbums.size();
    }

    public ArrayList<Pair<String, Bitmap>> getListAlbums() {
        return listAlbums;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
    public void setOnItemClickListener(AdapterAlbums.ClickListener listener) {
        AdapterAlbums.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAlbums.ViewHolderAlbums holder, int position) {
        String path = listAlbums.get(position).first;

        String album_name = path.substring(path.lastIndexOf("/") + 1);

        ((TextView) holder.album.findViewById(R.id.albumName)).setText(album_name);
        ((ImageView) holder.album.findViewById(R.id.idImage)).setImageBitmap(listAlbums.get(position).second);

        holder.album.setId(holder.album.getId()+position);
    }



    public class ViewHolderAlbums extends RecyclerView.ViewHolder implements View.OnClickListener{
        final RelativeLayout album;



        ViewHolderAlbums(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            album = itemView.findViewById(R.id.albumLayout);

        }



        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition(), view);
            }
        }
    }
}
