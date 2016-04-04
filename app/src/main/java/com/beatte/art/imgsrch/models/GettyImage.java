package com.beatte.art.imgsrch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author art.beatte
 * @version 2/1/16.
 */
public class GettyImage implements Parcelable {

    String caption;
    String title;
    DisplaySize[] display_sizes;

    public GettyImage() { }

    protected GettyImage(Parcel in) {
        caption = in.readString();
        title = in.readString();
        display_sizes = in.createTypedArray(DisplaySize.CREATOR);
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return display_sizes[0].uri;
    }

    public static final Creator<GettyImage> CREATOR = new Creator<GettyImage>() {
        @Override
        public GettyImage createFromParcel(Parcel in) {
            return new GettyImage(in);
        }

        @Override
        public GettyImage[] newArray(int size) {
            return new GettyImage[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(caption);
        dest.writeString(title);
        dest.writeTypedArray(display_sizes, flags);
    }
}
