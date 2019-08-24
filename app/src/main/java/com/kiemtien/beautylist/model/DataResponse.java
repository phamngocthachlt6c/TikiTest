package com.kiemtien.beautylist.model;

import com.google.gson.annotations.SerializedName;

public class DataResponse<T> {
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
