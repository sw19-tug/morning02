package com.gallery.android.gallery;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterTags extends RecyclerView.Adapter<AdapterTags.ViewHolderTags> {

    public final List<Tags> tags_ = new ArrayList<>();
    private static ClickDeleteListener listener_delete;
    private static ClickTickListener listener_tick;
    private ImageContainer actual_image;

    public AdapterTags(List<Tags> tags, ImageContainer imageContainer) {
        tags_.addAll(tags);
        actual_image = imageContainer;
    }

    public void addItem(Tags tag) {
        tags_.add(tag);

        notifyItemInserted(tags_.size()-1);
    }

    public void removeItem(int index) {

        if (index >= tags_.size())
            return;

        tags_.remove(index);
        notifyItemRemoved(index);
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
        CheckBox checkBox = holder.checkbox1;

        holder.tag_name.setText(actual_tag.getName());

        if (actual_image.tags.contains(tags_.get(position)))
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);
    }

    public void setOnItemDeleteClickListener(ClickDeleteListener listener) {
        AdapterTags.listener_delete = listener;
    }

    public void setOnItemTickListener(ClickTickListener listener) {
        AdapterTags.listener_tick = listener;
    }


    @Override
    public int getItemCount() {
        return tags_.size();
    }

    public class ViewHolderTags extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView tag_name;

        final CheckBox checkbox1;

        final Button delete_button;

        ViewHolderTags(View tagview) {
            super(tagview);
            delete_button = tagview.findViewById(R.id.button_tagitem_delete);
            delete_button.setOnClickListener(this);
            checkbox1=tagview.findViewById(R.id.checkbox_tagitem_tick);
            checkbox1.setOnClickListener(this);
            tag_name = tagview.findViewById(R.id.textview_tagitem_text);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.button_tagitem_delete :
                    listener_delete.onItemDeleteClick(getAdapterPosition(), view);
                    break;
                case R.id.checkbox_tagitem_tick:
                    listener_tick.onItemTick(getAdapterPosition(), view);
                    break;
            }
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

    public interface ClickDeleteListener {
        void onItemDeleteClick(int position, View v);
    }

    public interface ClickTickListener {
        void onItemTick(int position, View v);
    }
}
