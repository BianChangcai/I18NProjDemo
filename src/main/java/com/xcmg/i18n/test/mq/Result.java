package com.xcmg.i18n.test.mq;

import lombok.Data;
import org.apache.rocketmq.client.producer.SendResult;

@Data
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    public static Result<SendResult> success(SendResult sendResult) {
        return new Result().setCode("00000").setMsg("OK").setData(sendResult);
    }

    public Result setCode(String code) {
        this.code = code;
        return this;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

}
