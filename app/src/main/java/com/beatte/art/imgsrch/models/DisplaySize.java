package com.beatte.art.imgsrch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author art.beatte
 * @version 2/1/16.
 */
public class DisplaySize implements Parcelable {
    String name;
    String uri;

    public DisplaySize() { }

    protected DisplaySize(Parcel in) {
        name = in.readString();
        uri = in.readString();
    }

    public static final Creator<DisplaySize> CREATOR = new Creator<DisplaySize>() {
        @Override
        public DisplaySize createFromParcel(Parcel in) {
            return new DisplaySize(in);
        }

        @Override
        public DisplaySize[] newArray(int size) {
            return new DisplaySize[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uri);
    }
}
