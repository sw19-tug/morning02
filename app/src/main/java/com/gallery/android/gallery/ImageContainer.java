package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Date;
import java.util.Comparator;

public class ImageContainer implements ImageContainerInterface{

    private Bitmap image;
    private String path;
    private String filename;
    private Date date;
    private int height;
    private int width;
    private long size;
    private String orientation;


    enum PictureComperator implements Comparator<ImageContainer> {
        NAME {
            public int compare(ImageContainer p1, ImageContainer p2) {
                return p1.getPath().compareTo(p2.getPath());
            }
        },

        SIZE {
            public int compare(ImageContainer p1, ImageContainer p2) {
                return Long.compare(p1.getSize(), p2.getSize());

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

    public ImageContainer()
    {
        this.image = null;
        this.path = "";
        this.date = new Date();
    }

    public ImageContainer(String path, Date date, long size) {
        this.date = date;
        this.path = path;
        this.size = size;
    }

    public ImageContainer(String path)
    {
        this.image = BitmapFactory.decodeFile(path);
        this.path = path;
        this.date = new Date();
        this.filename = path.substring(path.lastIndexOf("/")+1);
    }

    public ImageContainer(String path,Date date, String name)
    {
        this.path = path;
        this.date = date;
        this.filename = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFilename(){return filename;}

    public void setFilename(String name){this.filename = name;}

    long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void setWidth(int width) {
        this.width = width;
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
}
