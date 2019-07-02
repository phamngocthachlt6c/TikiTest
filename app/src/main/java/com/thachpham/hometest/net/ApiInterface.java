package com.thachpham.hometest.net;

import com.thachpham.hometest.common.Constant;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

import java.util.List;

public interface ApiInterface {
    @GET(Constant.LINK_KEYWORD)
    Observable<Response<List<String>>> getKeywordList();
}
