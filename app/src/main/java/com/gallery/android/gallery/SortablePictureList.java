package com.gallery.android.gallery;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class SortablePictureList {


    List<PictureDummy> pictures;


    void sortByDate(boolean asc) {
        PictureDummy min_picture = pictures.get(0);
        List<PictureDummy> sorted_pictures = new ArrayList<>();

        while (sorted_pictures.size() < pictures.size()) {
            for (PictureDummy bitm: pictures) {

                if (bitm.getDate().before(min_picture.getDate())) {
                    min_picture = bitm;
                }

            }

            sorted_pictures.add(min_picture);
        }

        pictures.clear();
        pictures.addAll(sorted_pictures);

    }

    void sortByName() {

        PictureDummy min_picture = pictures.get(0);
        List<PictureDummy> sorted_pictures = new ArrayList<>();

        while (sorted_pictures.size() < pictures.size()) {
            for (PictureDummy bitm: pictures) {

                if (bitm.getFilename().compareTo(min_picture.getFilename()) > 0) {
                    min_picture = bitm;
                }

            }

            sorted_pictures.add(min_picture);
        }

        pictures.clear();
        pictures.addAll(sorted_pictures);

    }

    void sortBySize() {

    }



    Integer getPicture(Integer index) {


        return 0;
    }


}


