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

public class AdapterImages
        extends RecyclerView.Adapter<AdapterImages.ViewHolderImages>
        implements View.OnClickListener{

    ArrayList<ImageContainer> listImages;
    private View.OnClickListener listener;

    public AdapterImages(ArrayList<ImageContainer> listImages) {
        this.listImages = listImages;
    }

    @Override
    public ViewHolderImages onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout=0;

        layout=R.layout.item_grid_images;


        View view= LayoutInflater.from(parent.getContext()).inflate(layout,null,false);

        view.setOnClickListener(this);

        return new ViewHolderImages(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderImages holder, int position) {

        holder.photo.setImageBitmap(listImages.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderImages extends RecyclerView.ViewHolder {

        TextView etiName,etiInformation;
        ImageView photo;

        public ViewHolderImages(View itemView) {
            super(itemView);

            photo= (ImageView) itemView.findViewById(R.id.idImage);
        }
    }
}
