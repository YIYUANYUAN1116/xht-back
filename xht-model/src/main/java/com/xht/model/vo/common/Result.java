package com.xht.model.vo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "响应结果实体类")
public class Result<T> {

    //返回码
    @Schema(description = "业务状态码")
    private Integer errorCode;

    //返回码
    @Schema(description = "响应状态")
    private boolean success;

    //返回消息
    @Schema(description = "响应消息")
    private String errorMessage;

    //返回消息
    @Schema(description = "响应类型")
    private String showType;

    //返回数据
    @Schema(description = "业务数据")
    private T data;

    // 私有化构造
    private Result() {}

    // 返回数据
    public static <T> Result<T> build(T body,Boolean success, Integer code, String message) {
        Result<T> result = new Result<>();
        result.setData(body);
        result.setErrorCode(code);
        result.setErrorMessage(message);
        result.setSuccess(success);
        return result;
    }

    // 通过枚举构造Result对象
    public static <T> Result build(T body ,Boolean success, ResultCodeEnum resultCodeEnum) {
        return build(body ,success, resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }

    public static <T> Result build(Boolean success,ResultCodeEnum resultCodeEnum) {
        return build(null ,success, resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }

    public static <T> Result build(ResultCodeEnum resultCodeEnum) {
        return build(null ,true, resultCodeEnum.getCode() , resultCodeEnum.getMessage()) ;
    }
}
