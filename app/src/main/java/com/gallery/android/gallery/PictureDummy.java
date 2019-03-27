package com.gallery.android.gallery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Comparator;


public class PictureDummy {


    Date date;

    String filename;

    int size;


    enum PictureComperator implements Comparator<PictureDummy> {
        NAME {
            public int compare(PictureDummy p1, PictureDummy p2) {
                return p1.getFilename().compareTo(p2.getFilename());
            }
        },

        SIZE {
            public int compare(PictureDummy p1, PictureDummy p2) {
                return Integer.compare(p1.getSize(), p2.getSize());

            }
        },

        DATE {
            public int compare(PictureDummy p1, PictureDummy p2) {
                if (p1.getDate().before(p2.getDate())) {
                    return 1;
                }
                else if (p1.getDate().after(p2.getDate())) {
                    return -1;
                }
                else
                    return 0;
            }
        }


    }


    public static Comparator<PictureDummy> decending(final Comparator<PictureDummy> other) {
        return new Comparator<PictureDummy>() {
            public int compare(PictureDummy o1, PictureDummy o2) {
                return -1 * other.compare(o1, o2);
            }
        };
    }

    public static Comparator<PictureDummy> ascending(final Comparator<PictureDummy> other) {
        return new Comparator<PictureDummy>() {
            public int compare(PictureDummy o1, PictureDummy o2) {
                return 1 * other.compare(o1, o2);
            }
        };
    }


    public static Comparator<PictureDummy> getComparator(final PictureComperator... multipleOptions) {
        return new Comparator<PictureDummy>() {
            public int compare(PictureDummy o1, PictureDummy o2) {
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

    PictureDummy(Date date, String filename, int size) {
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
