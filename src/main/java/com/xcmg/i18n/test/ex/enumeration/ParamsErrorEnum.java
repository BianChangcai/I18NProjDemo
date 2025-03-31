package com.xcmg.i18n.test.ex.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum ParamsErrorEnum implements CodeEnum {

    ERROR_CODE_10000("10000", "参数错误"),
    ERROR_CODE_10001("10001", "不支持的请求方式"),
    ERROR_CODE_10002("10002", "参数格式异常"),
    ;

    private final String code;

    private final String msg;

}
