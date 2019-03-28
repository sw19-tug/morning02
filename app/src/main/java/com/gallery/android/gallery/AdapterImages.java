package com.gallery.android.gallery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.gallery.android.gallery.ImageContainer.PictureComperator.DATE;
import static com.gallery.android.gallery.ImageContainer.PictureComperator.NAME;
import static com.gallery.android.gallery.ImageContainer.PictureComperator.SIZE;


public class AdapterImages {


    enum SortOrder {
        ASCENDING, DESCENDING
    }


    List<ImageContainer> pictures;

    AdapterImages() {
        pictures = new ArrayList<>();
    }




    void sortByDate(SortOrder so) {
       if (so == SortOrder.ASCENDING)
           Collections.sort(pictures, ImageContainer.ascending(ImageContainer.getComparator(DATE)));
       else
           Collections.sort(pictures, ImageContainer.decending(ImageContainer.getComparator(DATE)));

    }

    void sortByName(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(pictures, ImageContainer.ascending(ImageContainer.getComparator(NAME)));
        else
            Collections.sort(pictures, ImageContainer.decending(ImageContainer.getComparator(NAME)));

    }




    void sortBySize(SortOrder so) {
        if (so == SortOrder.ASCENDING)
            Collections.sort(pictures, ImageContainer.ascending(ImageContainer.getComparator(SIZE)));
        else
            Collections.sort(pictures, ImageContainer.decending(ImageContainer.getComparator(SIZE)));
    }




    ImageContainer getPicture(Integer index) {

        return pictures.get(index);
    }


}



