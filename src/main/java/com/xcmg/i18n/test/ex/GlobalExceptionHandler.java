package com.xcmg.i18n.test.ex;

import com.xcmg.i18n.test.ex.baseentity.BaseRes;
import com.xcmg.i18n.test.ex.enumeration.CodeEnum;
import com.xcmg.i18n.test.ex.exception.BusinessException;
import com.xcmg.i18n.test.ex.exception.ParameterException;
import com.xcmg.i18n.test.ex.exception.PlatformException;
import com.xcmg.i18n.test.ex.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ResponseBody
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 不支持的请求方始
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public BaseRes<?> methodNotSupportExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("不支持的请求方式", e);
        return BaseRes.buildFailure(CodeEnum.ParamsErrorEnum.ERROR_CODE_10001.getCode(), e.getMessage());
    }

    /**
     * 参数类型错误
     */
    @ExceptionHandler(value = {BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseRes<?> bindExceptionHandler(BindException e) {
        log.error("====参数类型错误===", e);
        return BaseRes.buildFailure(CodeEnum.ParamsErrorEnum.ERROR_CODE_10000);
    }

    /**
     * 参数格式问题
     */
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class,
            HttpMessageConversionException.class, javax.validation.UnexpectedTypeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseRes<?> httpMessageConversionExceptionHandler(Exception e) {
        log.error("====参数格式异常===", e);
        return BaseRes.buildFailure(CodeEnum.ParamsErrorEnum.ERROR_CODE_10002);
    }

    /**
     * 参数错误
     */
    @ExceptionHandler(value = ParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseRes<?> parameterErrorExceptionHandler(ParameterException e) {
        log.error("====参数异常:code:{},msg:{}", e.getCode(), e.getMessage(), e);
        return BaseRes.buildFailure(e.getCode(), e.getMessage());
    }

    /**
     * 业务异常，给前台返回异常数据
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseRes<?> businessExceptionHandler(BusinessException e) {
        log.error("====业务异常:code:{},msg:{},showMsg:{}", e.getCode(), e.getMessage(), e.getShowMsg(), e);
        return BaseRes.buildFailure(e.getCode(), e.getShowMsg());
    }

    /**
     * RPC，给前台返回异常数据
     */
    @ExceptionHandler(value = RpcException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseRes<?> rpcExceptionHandler(RpcException e) {
        log.error("====RPC异常:code:{},msg:{},showMsg:{}", e.getCode(), e.getMessage(), e.getShowMsg(), e);
        return BaseRes.buildFailure(e.getCode(), e.getShowMsg());
    }

    /**
     * 运行异常，给前台返回异常数据
     */
    @ExceptionHandler(value = PlatformException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseRes<?> rpcExceptionHandler(PlatformException e) {
        log.error("====运行异常:code:{},msg:{},showMsg:{}", e.getCode(), e.getMessage(), e.getShowMsg(), e);
        return BaseRes.buildFailure(e.getCode(), e.getShowMsg());
    }

}
