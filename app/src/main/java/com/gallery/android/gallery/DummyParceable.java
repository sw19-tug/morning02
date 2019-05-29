package com.gallery.android.gallery;

import android.os.Parcel;
import android.os.Parcelable;

public class DummyParceable implements Parcelable {

    int value1;


    DummyParceable(int value) {
        value1 = value;
    }

    protected DummyParceable(Parcel in) {
        value1 = in.readInt();
    }

    public static final Creator<DummyParceable> CREATOR = new Creator<DummyParceable>() {
        @Override
        public DummyParceable createFromParcel(Parcel in) {

            return new DummyParceable(in);
        }

        @Override
        public DummyParceable[] newArray(int size) {
            return new DummyParceable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeValue(value1);
    }
}
