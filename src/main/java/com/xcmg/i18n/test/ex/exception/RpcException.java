package com.xcmg.i18n.test.ex.exception;

import com.xcmg.i18n.test.ex.enumeration.CodeEnum;
import lombok.Getter;

@Getter
public class RpcException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 799633539625676004L;

    /**
     * 返回错误码
     */
    private final String code;

    /**
     * 展示信息
     */
    private final String showMsg;

    /**
     * RpcException-处理未知的异常
     *
     * @param rpcErrorEnum rpcErrorEnum
     */
    public RpcException(CodeEnum.RpcErrorEnum rpcErrorEnum) {
        super(rpcErrorEnum.getMsg());
        this.code = rpcErrorEnum.getCode();
        this.showMsg = CodeEnum.ErrorCodeEnum.ERROR_CODE_FAIL.getMsg();
    }

    /**
     * RpcException 处理已知的异常
     *
     * @param rpcErrorEnum rpcErrorEnum
     * @param code         code
     * @param showMsg      showMsg
     */
    public RpcException(CodeEnum.RpcErrorEnum rpcErrorEnum, String code, String showMsg) {
        super(rpcErrorEnum.getMsg());
        this.code = rpcErrorEnum.getCode() + "_" + code;
        this.showMsg = showMsg;
    }

    /**
     * RpcException 处理已知的异常
     *
     * @param rpcErrorEnum rpcErrorEnum
     * @param code         code
     * @param showMsg      showMsg
     */
    public RpcException(CodeEnum.RpcErrorEnum rpcErrorEnum, String code, String msg, String showMsg) {
        super(msg);
        this.code = rpcErrorEnum.getCode() + "_" + code;
        this.showMsg = showMsg;
    }

}
