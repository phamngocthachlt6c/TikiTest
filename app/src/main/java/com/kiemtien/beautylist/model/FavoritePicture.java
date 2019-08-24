package com.kiemtien.beautylist.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoritePicture {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "picture_id")
    private String pictureId;
    @ColumnInfo(name = "link_small")
    private String smallLink;
    @ColumnInfo(name = "link_large")
    private String largeLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getSmallLink() {
        return smallLink;
    }

    public void setSmallLink(String smallLink) {
        this.smallLink = smallLink;
    }

    public String getLargeLink() {
        return largeLink;
    }

    public void setLargeLink(String largeLink) {
        this.largeLink = largeLink;
    }
}
