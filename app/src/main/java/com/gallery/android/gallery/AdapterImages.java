package com.gallery.android.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gallery.android.gallery.ImageContainer.PictureComperator.DATE;
import static com.gallery.android.gallery.ImageContainer.PictureComperator.NAME;
import static com.gallery.android.gallery.ImageContainer.PictureComperator.SIZE;

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolderImages> {

    private final ArrayList<ImageContainer> listImages;
    private static ClickListener listener;
    private static LongClickListener long_click_listener;
    enum SortOrder {ASCENDING, DESCENDING}

    public AdapterImages() {
        this.listImages = new ArrayList<>();
    }

    public AdapterImages(ArrayList<ImageContainer> listImages) {
        this.listImages = new ArrayList<>(listImages);
    }

    @Override
    @NonNull
    public ViewHolderImages onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        int layout;
        layout = R.layout.item_grid_images;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,null,false);
        return new ViewHolderImages(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderImages holder, int position) {
        holder.photo.setImageBitmap(listImages.get(position).getImage());
        holder.photo.setId(holder.photo.getId()+position);
    }

    @Override
    public int getItemCount() {
        return listImages.size();
    }

    public ArrayList<ImageContainer> getListImages() {
        return listImages;
    }

    public void replaceItems(List<ImageContainer> new_list) {

        try {
            listImages.clear();
            if (!new_list.isEmpty())
                listImages.addAll(new_list);

            notifyDataSetChanged();
        }
        catch (NullPointerException nullptr_exeption ) {
            nullptr_exeption.printStackTrace();
        }
    }

    public void addItems(List<ImageContainer> new_list) {

        if (!new_list.isEmpty()) {
            listImages.addAll(new_list);
            notifyDataSetChanged();
        }
    }

    public void addItem(ImageContainer new_item) {
        listImages.add(new_item);
        notifyDataSetChanged();
    }

    public String searchPictures(String name){
        for(ImageContainer image : listImages)
        {
            if(image.getFilename().equals(name))
            {
                return image.getPath();
            }
        }
        return null;
    }

    void sortByDate(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(listImages, ImageContainer.ascending(ImageContainer.getComparator(DATE)));
        else
            Collections.sort(listImages, ImageContainer.decending(ImageContainer.getComparator(DATE)));
    }

    void sortByName(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(listImages, ImageContainer.ascending(ImageContainer.getComparator(NAME)));
        else
            Collections.sort(listImages, ImageContainer.decending(ImageContainer.getComparator(NAME)));
    }

    void sortBySize(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(listImages, ImageContainer.ascending(ImageContainer.getComparator(SIZE)));
        else
            Collections.sort(listImages, ImageContainer.decending(ImageContainer.getComparator(SIZE)));
    }

    public void setOnItemClickListener(ClickListener listener) {
        AdapterImages.listener = listener;
    }

    public void setOnItemLongClickListener(LongClickListener listener) {
        AdapterImages.long_click_listener = listener;
    }

    public class ViewHolderImages extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final ImageView photo;

        ViewHolderImages(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            photo= itemView.findViewById(R.id.idImage);
        }

        @Override
        public void onClick(View view) {
            if (listener!=null){
                listener.onItemClick(getAdapterPosition(), view);
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (long_click_listener !=null) {
                long_click_listener.onItemLongClick(getAdapterPosition(), view);
                return true;
            }
            else
                return false;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public interface LongClickListener {
        void onItemLongClick(int position, View v);
    }
}
