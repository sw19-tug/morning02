package com.gallery.android.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterTags extends RecyclerView.Adapter<AdapterTags.ViewHolderTags> {

    private final Map<Integer, String> tags_ = new HashMap<>();


    public AdapterTags(Map<Integer, String> tags) {tags_.putAll(tags);}


    @Override
    @NonNull
    public ViewHolderTags onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout;
        layout = R.layout.item_tag;

        View view= LayoutInflater.from(parent.getContext()).inflate(layout,null,false);
        return new ViewHolderTags(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTags holder, int position) {
        holder.tag_name.setText("ddd");
        holder.tag_name.setId(position);
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolderTags extends RecyclerView.ViewHolder {

        final TextView tag_name;

        ViewHolderTags(View tagview) {
            super(tagview);

            tag_name = tagview.findViewById(R.id.tag_name);



        }


    }






}
