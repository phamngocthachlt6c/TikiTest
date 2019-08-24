package com.kiemtien.beautylist.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import com.google.gson.annotations.SerializedName;

public class ImageUrl implements Parcelable {
    @ColumnInfo(name = "origin")
    @SerializedName("origin")
    private String origin;
    @ColumnInfo(name = "large")
    @SerializedName("large")
    private String large;
    @ColumnInfo(name = "medium")
    @SerializedName("medium")
    private String medium;
    @ColumnInfo(name = "small")
    @SerializedName("small")
    private String small;

    public ImageUrl(){}

    protected ImageUrl(Parcel in) {
        origin = in.readString();
        large = in.readString();
        medium = in.readString();
        small = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(origin);
        dest.writeString(large);
        dest.writeString(medium);
        dest.writeString(small);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageUrl> CREATOR = new Creator<ImageUrl>() {
        @Override
        public ImageUrl createFromParcel(Parcel in) {
            return new ImageUrl(in);
        }

        @Override
        public ImageUrl[] newArray(int size) {
            return new ImageUrl[size];
        }
    };

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
