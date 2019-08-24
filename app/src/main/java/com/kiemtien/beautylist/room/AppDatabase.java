package com.kiemtien.beautylist.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.kiemtien.beautylist.model.FavoritePicture;

@Database(entities = {FavoritePicture.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoritePictureDao favoritePictureDao();
}
