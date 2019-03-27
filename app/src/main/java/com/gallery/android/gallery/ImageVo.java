package com.gallery.android.gallery;

public class ImageVo {

    private int photo;

    public ImageVo(){
    }

    public ImageVo(int photo){
        this.photo = photo;
    }

    public int getPhoto(){
        return photo;
    }

    public void setPhoto(int photo){
        this.photo = photo;
    }
}
