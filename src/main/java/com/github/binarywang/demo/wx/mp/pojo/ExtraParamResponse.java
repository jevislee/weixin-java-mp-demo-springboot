package com.github.binarywang.demo.wx.mp.pojo;

public class ExtraParamResponse<T> extends BaseResponse {

    public T data;

    public ExtraParamResponse(BaseResponse baseResponse, T data) {
        super(baseResponse.getCode(), baseResponse.getMessage());
        this.data = data;
    }

    public ExtraParamResponse(T data) {
        this(BaseResponse.SUCCESS, data);
    }
}
