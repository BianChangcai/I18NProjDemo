package com.xcmg.i18n.test.ex.appl;

import com.xcmg.i18n.test.ex.baseentity.BaseRes;
import com.xcmg.i18n.test.ex.baseentity.UserDemoVO;
import com.xcmg.i18n.test.ex.enumeration.CodeEnum;
import com.xcmg.i18n.test.ex.exception.BusinessException;
import com.xcmg.i18n.test.ex.exception.ParameterException;
import com.xcmg.i18n.test.ex.exception.PlatformException;
import com.xcmg.i18n.test.ex.exception.RpcException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class ExpApplController {

    //@ApiOperation("parameterExceptionEnum")
    //@LogIndex
    @GetMapping("parameterExceptionEnum")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> parameterExceptionEnum() {
        throw new ParameterException(CodeEnum.ParamsErrorEnum.ERROR_CODE_10002);
    }

//    {
//        "success":false,
//            "data":null,
//            "msg":"参数格式异常",
//            "code":"10002"
//    }


    //@ApiOperation("parameterExceptionMsg")
    //@LogIndex
    @GetMapping("parameterExceptionMsg")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> parameterExceptionMsg() {
        throw new ParameterException("用户Id不能为空");
    }


//    {
//        "success":false,
//            "data":null,
//            "msg":"用户Id不能为空",
//            "code":"10000"
//    }

    //@ApiOperation("businessExceptionEnum")
    //@LogIndex
    @GetMapping("businessExceptionEnum")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> businessExceptionEnum() {
        throw new BusinessException(CodeEnum.BusinessErrorEnum.ERROR_CODE_20001);
    }


//    {
//        "success":false,
//            "data":null,
//            "msg":"您有一个订单正在创建，请稍后查看",
//            "code":"20001"
//    }

    //@ApiOperation("businessExceptionMsg")
    //@LogIndex
    @GetMapping("businessExceptionMsg")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> businessExceptionMsg() {
        throw new BusinessException("用户创建失败");
    }

//    {
//        "success":false,
//            "data":null,
//            "msg":"用户创建失败",
//            "code":"20000"
//    }

    //@ApiOperation("rpcExceptionDefaultEnum")
    //@LogIndex
    @GetMapping("rpcExceptionDefaultEnum")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> rpcExceptionDefaultEnum() {
        throw new RpcException(CodeEnum.RpcErrorEnum.ERROR_CODE_USER_0_30001_0001);
    }

//    {
//        "success":false,
//            "data":null,
//            "msg":"系统太火爆了，请稍后重试!",
//            "code":"USER_0_30001_0001"
//    }

    //@ApiOperation("rpcExceptionEnumShowMsg")
    //@LogIndex
    @GetMapping("rpcExceptionEnumShowMsg")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> rpcExceptionEnumShowMsg() {
        throw new RpcException(CodeEnum.RpcErrorEnum.ERROR_CODE_USER_1_30001_0001, "1000", "用户不存在");
    }

//    {
//        "success":false,
//            "data":null,
//            "msg":"用户不存在",
//            "code":"USER_1_30001_0001_1000"
//    }

    //@ApiOperation("rpcExceptionEnumMsg")
    //@LogIndex
    @GetMapping("rpcExceptionEnumMsg")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> rpcExceptionEnumMsg() {
        throw new RpcException(CodeEnum.RpcErrorEnum.ERROR_CODE_USER_1_30001_0001,
                "1000", "底层结构异常", "用户不存在");
    }


//    {
//        "success":false,
//            "data":null,
//            "msg":"用户不存在",
//            "code":"USER_1_30001_0001_1000"
//    }

    //@ApiOperation("platformException")
    //@LogIndex
    @GetMapping("platformException")
    @ResponseBody
    public BaseRes<List<UserDemoVO>> platformException() {
        throw new PlatformException(CodeEnum.PlatformErrorEnum.ERROR_CODE_40001);
    }

//    {
//        "success":false,
//            "data":null,
//            "msg":"系统太火爆了，请稍后重试!",
//            "code":"40001"
//    }

}
