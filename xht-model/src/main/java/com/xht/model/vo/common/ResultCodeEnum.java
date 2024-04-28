package com.xht.model.vo.common;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200 , "操作成功!") ,

    VALIDATE_CODE_ERROR(202 , "验证码错误!") ,
    VALIDATE_CODE_EXPIRED(203 , "验证码过期!") ,

    USER_NAME_IS_EXISTS(209 , "用户名已经存在!"),

    ACCOUNT_STOP( 216, "账号已停用!"),
    LOGOUT_SUCCESS(200, "注销成功!"),
    AUTHENTICATION_SUCCESS(200, "登录成功!"),

    ACCESS_DENIED(403, "权限不足，访问被拒绝!"),
    NOT_AUTHENTICATION(401, "用户未登录，请登录!"),
    LOGIN_ERROR(401 , "用户名或者密码错误!"),
    TOKEN_EXPIRED(401, "认证已过期，请重新登录!"),

    JWT_TOKEN_ERROR(405,"令牌异常!"),
    SYSTEM_ERROR2(999,"系统异常!"),

    SYSTEM_ERROR(9999 , "您的网络有问题请稍后重试!"),
    NODE_ERROR( 217, "该节点下有子节点，不可以删除"),
    DATA_ERROR(204, "数据异常!"),

    STOCK_LESS( 219, "库存不足!"),

    //ACTIVTI
    CANNOT_DID(999,"当前用户不能处理改任务");
;

    private final Integer code ;      // 业务状态码
    private final String message ;    // 响应消息

    private ResultCodeEnum(Integer code , String message) {
        this.code = code ;
        this.message = message ;
    }

}
