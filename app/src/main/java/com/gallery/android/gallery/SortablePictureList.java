package com.gallery.android.gallery;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.gallery.android.gallery.PictureDummy.PictureComperator.DATE;
import static com.gallery.android.gallery.PictureDummy.PictureComperator.NAME;
import static com.gallery.android.gallery.PictureDummy.PictureComperator.SIZE;


public class SortablePictureList {

    enum SortOrder {
        ASCENDING, DESCENDING
    }


    List<PictureDummy> pictures;

    SortablePictureList() {
        pictures = new ArrayList<>();
    }




    void sortByDate(SortOrder so) {
       if (so == SortOrder.ASCENDING)
           Collections.sort(pictures, PictureDummy.ascending(PictureDummy.getComparator(DATE)));
       else
           Collections.sort(pictures, PictureDummy.decending(PictureDummy.getComparator(DATE)));

    }

    void sortByName(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(pictures, PictureDummy.ascending(PictureDummy.getComparator(NAME)));
        else
            Collections.sort(pictures, PictureDummy.decending(PictureDummy.getComparator(NAME)));

    }




    void sortBySize(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(pictures, PictureDummy.ascending(PictureDummy.getComparator(SIZE)));
        else
            Collections.sort(pictures, PictureDummy.decending(PictureDummy.getComparator(SIZE)));
    }




    PictureDummy getPicture(Integer index) {

        return pictures.get(index);
    }


}



