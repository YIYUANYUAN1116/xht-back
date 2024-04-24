package com.xht.common.service.exception;

import com.xht.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2023/12/19  22:51
 */

@Data
public class XhtException extends RuntimeException{
    private Integer code;
    private String message;

    public XhtException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    public XhtException(String message , Integer code) {
       this.code = code;
       this.message = message;
    }
}
