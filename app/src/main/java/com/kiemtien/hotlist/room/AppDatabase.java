package com.kiemtien.hotlist.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.kiemtien.hotlist.model.FavoritePicture;

@Database(entities = {FavoritePicture.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritePictureDao favoritePictureDao();
}
