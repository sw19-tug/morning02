package com.gallery.android.gallery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CHENAO on 3/07/2017.
 */

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolderImages>
        /*implements View.OnClickListener*/{

    ArrayList<ImageContainer> listImages;
    private static ClickListener listener;

    public AdapterImages(ArrayList<ImageContainer> listImages) {
        this.listImages = listImages;
    }

    @Override
    public ViewHolderImages onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;

        layout=R.layout.item_grid_images;

        View view= LayoutInflater.from(parent.getContext()).inflate(layout,null,false);
        return new ViewHolderImages(view);
    }


    @Override
    public void onBindViewHolder(ViewHolderImages holder, int position) {

        holder.photo.setImageBitmap(listImages.get(position).getImage());
        holder.photo.setId(holder.photo.getId()+position);
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    public void setOnClickListener(ClickListener listener){
        AdapterImages.listener = listener;
    }

    public class ViewHolderImages extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView etiName,etiInformation;
        ImageView photo;

        public ViewHolderImages(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            photo= (ImageView) itemView.findViewById(R.id.idImage);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onItemClick(getAdapterPosition(), view);
            }
        }
    }

    public void setOnItemClickListener(ClickListener listener) {
        AdapterImages.listener = listener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
