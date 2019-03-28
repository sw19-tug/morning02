package com.gallery.android.gallery;

import java.util.Date;
import java.util.Comparator;


public class ImageContainer {


    Date date;

    String filename;

    int size;


    enum PictureComperator implements Comparator<ImageContainer> {
        NAME {
            public int compare(ImageContainer p1, ImageContainer p2) {
                return p1.getFilename().compareTo(p2.getFilename());
            }
        },

        SIZE {
            public int compare(ImageContainer p1, ImageContainer p2) {
                return Integer.compare(p1.getSize(), p2.getSize());

            }
        },

        DATE {
            public int compare(ImageContainer p1, ImageContainer p2) {
                if (p1.getDate().before(p2.getDate())) {
                    return -1;
                }
                else if (p1.getDate().after(p2.getDate())) {
                    return 1;
                }
                else
                    return 0;
            }
        }


    }


    public static Comparator<ImageContainer> decending(final Comparator<ImageContainer> other) {
        return new Comparator<ImageContainer>() {
            public int compare(ImageContainer o1, ImageContainer o2) {
                return -1 * other.compare(o1, o2);
            }
        };
    }

    public static Comparator<ImageContainer> ascending(final Comparator<ImageContainer> other) {
        return new Comparator<ImageContainer>() {
            public int compare(ImageContainer o1, ImageContainer o2) {
                return 1 * other.compare(o1, o2);
            }
        };
    }


    public static Comparator<ImageContainer> getComparator(final PictureComperator... multipleOptions) {
        return new Comparator<ImageContainer>() {
            public int compare(ImageContainer o1, ImageContainer o2) {
                for (PictureComperator option : multipleOptions) {
                    int result = option.compare(o1, o2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            }
        };
    }

    ImageContainer(Date date, String filename, int size) {
        this.date = date;
        this.filename = filename;
        this.size = size;
    }


    Date getDate() {

        return date;
    }

    String getFilename() {
        return filename;
    }

    int getSize() {
        return size;
    }




}
