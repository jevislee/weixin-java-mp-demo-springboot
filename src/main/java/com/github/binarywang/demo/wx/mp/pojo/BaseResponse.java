package com.github.binarywang.demo.wx.mp.pojo;

/**
 * 通用响应封装
 */
public class BaseResponse {

    public static final int CODE_SUCCESS = 200;//操作成功
    public static final int CODE_INVALID_PARAM = 1001;//参数无效
    public static final int CODE_SYSTEM_BUSY = 1002;//系统繁忙(操作失败)
    public static final int CODE_LOGIN_FAILED = 1003;//登录失败

    public static final BaseResponse SUCCESS = new BaseResponse(CODE_SUCCESS, null);
    public static final BaseResponse SYSTEM_BUSY = new BaseResponse(CODE_SYSTEM_BUSY, "系统繁忙，请稍后再试");

    protected Integer code;

    protected String message;

    public BaseResponse() {
        this(BaseResponse.SUCCESS);
    }

    public BaseResponse(BaseResponse value) {
        this(value.code, value.message);
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static BaseResponse invalidParam(String paramName) {
        return new BaseResponse(CODE_INVALID_PARAM, "参数错误:" + paramName);
    }

    public static BaseResponse loginFailed(String reason) {
        return new BaseResponse(CODE_LOGIN_FAILED, "登录失败:" + reason);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof BaseResponse)) {
            return false;
        }

        if (((BaseResponse) obj).getCode() != this.getCode()) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "BaseResponse [code=" + code + ", message=" + message + "]";
    }
}
