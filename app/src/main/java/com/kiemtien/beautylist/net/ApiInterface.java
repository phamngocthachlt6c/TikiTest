package com.kiemtien.beautylist.net;

import com.kiemtien.beautylist.model.CommonConfig;
import com.kiemtien.beautylist.model.DataResponse;
import com.kiemtien.beautylist.common.Constant;
import com.kiemtien.beautylist.model.Category;
import com.kiemtien.beautylist.model.Picture;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ApiInterface {
    @GET(Constant.API_GET_CATEGORIES)
    Observable<Response<DataResponse<List<Category>>>> fetCategories();
    @GET(Constant.API_GET_PICTURES)
    Observable<Response<DataResponse<List<Picture>>>> fetPictures(@Query("category_id") String categoryId);
    @GET(Constant.API_GET_COMMON_CONFIG)
    Observable<Response<DataResponse<CommonConfig>>> fetCommonConfig();
}
