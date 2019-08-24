package com.kiemtien.beautylist.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("image_url")
    private ImageUrl imageUrl;
    @SerializedName("update_at")
    private String updateAt;
    @SerializedName("is_today_update")
    private boolean isTodayUpdate;

    public Category() {

    }

    protected Category(Parcel in) {
        id = in.readString();
        name = in.readString();
        imageUrl = in.readParcelable(ImageUrl.class.getClassLoader());
        updateAt = in.readString();
        isTodayUpdate = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(imageUrl, flags);
        dest.writeString(updateAt);
        dest.writeByte((byte) (isTodayUpdate ? 1 : 0));
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ImageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public boolean isTodayUpdate() {
        return isTodayUpdate;
    }

    public void setTodayUpdate(boolean todayUpdate) {
        isTodayUpdate = todayUpdate;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
