package com.kiemtien.hotlist.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.kiemtien.hotlist.model.FavoritePicture;
import io.reactivex.Completable;

import java.util.List;

@Dao
public interface FavoritePictureDao {
    @Query("select * from favoritepicture")
    List<FavoritePicture> getAllFavorites();

    @Query("SELECT * FROM favoritepicture WHERE picture_id = :pictureId")
    FavoritePicture findFavoriteById(String pictureId);

    @Insert
    Completable insert(FavoritePicture pictures);

    @Delete
    Completable delete(FavoritePicture picture);

//    @Query("DELETE FROM favoritepicture WHERE picture_id = :pictureId")
//    Completable deleteFavoriteById(String pictureId);
}
