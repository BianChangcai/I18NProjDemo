package com.xcmg.i18n.test.ex.baseentity;

import com.xcmg.i18n.test.ex.enumeration.CodeEnum;

public class BaseRes<T> {

    private String statusCode;
    private String message;
    private T data;

    public BaseRes(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public BaseRes(String statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static BaseRes<?> buildFailure(String code, String showMsg) {
        return new BaseRes(code, showMsg);
    }

    public static BaseRes<?> buildFailure(CodeEnum.ParamsErrorEnum errorCode10000) {
        return new BaseRes(errorCode10000.getCode(), errorCode10000.getMsg());
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
