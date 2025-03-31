package com.xcmg.i18n.test.ex.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum BusinessErrorEnum implements CodeEnum {

    ERROR_CODE_20000("20000", "业务处理失败", "系统太火爆了，请稍后重试!"),
    ERROR_CODE_20001("20001", "订单创建失败", "您有一个订单正在创建，请稍后查看"),
    ERROR_CODE_20002("20002", "付款失败，存在创建中的订单", "您的订单付款失败，请稍后查看"),
    ERROR_CODE_20003("20003", "付款失败，存在未支付的订单", "您的订单付款失败，请稍后查看"),
    ;

    private final String code;

    private final String msg;

    private final String showMsg;

}
