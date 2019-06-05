package com.gallery.android.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class ImageContainer implements ImageContainerInterface, Parcelable {

    private Bitmap image;
    private String path;
    private String filename;
    private Date date;
    public Set<Tags> tags = new HashSet<>();

    protected ImageContainer(Parcel in) {
        image = in.readParcelable(Bitmap.class.getClassLoader());
        path = in.readString();
        filename = in.readString();
        size = in.readInt();
    }

    public static final Creator<ImageContainer> CREATOR = new Creator<ImageContainer>() {
        @Override
        public ImageContainer createFromParcel(Parcel in) {
            return new ImageContainer(in);
        }

        @Override
        public ImageContainer[] newArray(int size) {
            return new ImageContainer[size];
        }
    };

    public Set<Tags> getTags() {
        return tags;
    }

    int size;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeValue(image);
        dest.writeString(path);
        dest.writeString(filename);
        dest.writeSerializable(date);
        dest.writeValue(tags);
    }

    enum PictureComperator implements Comparator<ImageContainer> {
        NAME {
            public int compare(ImageContainer p1, ImageContainer p2) {
                return p1.getPath().compareTo(p2.getPath());
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

    public ImageContainer()
    {
        this.image = null;
        this.path = "";
        this.date = new Date();
    }

    public ImageContainer(String path, Date date, int size) {
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

    public ImageContainer(String path, Date date, String name, Set<Tags> tags)
    {
        this.path = path;
        this.date = date;
        this.filename = name;
        this.tags = tags;
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

    int getSize() {
        return size;
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
