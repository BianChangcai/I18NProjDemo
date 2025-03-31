package com.xcmg.i18n.test.ex.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum ErrorCodeEnum implements CodeEnum {

    ERROR_CODE_SUCCESS("00000", "成功"),
    ERROR_CODE_PARAMS_ERROR("10000", "参数错误"),
    ERROR_CODE_BUSINESS_ERROR("20000", "业务处理失败"),
    ERROR_CODE_PRC_ERROR("30000", "RPC处理失败"),
    ERROR_CODE_RUNTIME_ERROR("40000", "运行时失败"),
    ERROR_CODE_FAIL("99999", "系统太火爆了，请稍后重试!"),
    ;

    private final String code;

    private final String msg;

}

