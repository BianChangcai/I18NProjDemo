package com.xcmg.i18n.test.ex.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum RpcErrorEnum implements CodeEnum {

    /**
     * 系统_失败分类（请求0、返回1）_业务_方法_调用方CODE码（代码补齐）
     */
    ERROR_CODE_USER_0_30001_0001("USER_0_30001_0001", "RPC异常-USER-用户信息-查询单个用户信息-接口调用失败"),
    ERROR_CODE_USER_1_30001_0001("USER_1_30001_0001", "RPC异常-USER-用户信息-查询单个用户信息-接口返回失败"),
    ERROR_CODE_USER_1_30001_0002("USER_1_30001_0002", "RPC异常-USER-用户信息-分页查询用户信息-接口返回失败"),
    ;

    private final String code;

    private final String msg;

}


