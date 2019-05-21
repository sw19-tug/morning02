package com.gallery.android.gallery;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdapterTags extends RecyclerView.Adapter<AdapterTags.ViewHolderTags> {

    private final List<Tags> tags_ = new ArrayList<>();


    public AdapterTags(List<Tags> tags) {tags_.addAll(tags);}

    public void addItem(String name) {
        Tags new_tag = new Tags(name);
        tags_.add(new_tag);

        notifyItemInserted(tags_.size()-1);
    }


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


        Tags actual_tag = tags_.get(position);

        TextView textView = holder.tag_name;

        textView.setText(actual_tag.getName());
        holder.tag_name.setId(position);
    }


    @Override
    public int getItemCount() {
        return tags_.size();
    }

    public class ViewHolderTags extends RecyclerView.ViewHolder {

        public TextView tag_name;

        ViewHolderTags(View tagview) {
            super(tagview);

            tag_name = tagview.findViewById(R.id.tag_name);
        }

    }
    public boolean hasItem(String name){
            boolean flag = false;
            for (int k = 0; k < tags_.size(); k++)
            {
                Tags tag;
                tag = tags_.get(k);
                if (tag.getName().equals(name))
                {
                    flag = true;
                    break;
                }
            }
            return flag;

    }


}
