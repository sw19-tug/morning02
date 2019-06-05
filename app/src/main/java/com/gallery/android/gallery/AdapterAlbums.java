package com.gallery.android.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAlbums extends RecyclerView.Adapter<AdapterAlbums.ViewHolderAlbums>  {
    private final ArrayList<String> listAlbums;
    private static AdapterAlbums.ClickListener listener;

    public AdapterAlbums(ArrayList<String> listAlbums) {
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

    public ArrayList<String> getListAlbums() {
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
        ((TextView) holder.album.findViewById(R.id.albumName)).setText(listAlbums.get(position));
        holder.album.setId(holder.album.getId()+position);

    }

    public class ViewHolderAlbums extends RecyclerView.ViewHolder implements View.OnClickListener{
        final RelativeLayout album;


        ViewHolderAlbums(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            album= itemView.findViewById(R.id.albumLayout);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition(), view);
            }
        }
    }
}
