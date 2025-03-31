package com.xcmg.i18n.test.ex.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum PlatformErrorEnum implements CodeEnum {

    ERROR_CODE_40000("40000", "运行时失败"),
    ERROR_CODE_40001("40001", "路由消息处理失败"),
    ;

    private final String code;

    private final String msg;

}
