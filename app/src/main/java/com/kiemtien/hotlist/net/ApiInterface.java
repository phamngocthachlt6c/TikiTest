package com.kiemtien.hotlist.net;

import com.kiemtien.hotlist.model.DataResponse;
import com.kiemtien.hotlist.common.Constant;
import com.kiemtien.hotlist.model.Category;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

import java.util.List;

public interface ApiInterface {
    @GET(Constant.LINK_KEYWORD)
    Observable<Response<DataResponse<List<Category>>>> fetCategories();
}
