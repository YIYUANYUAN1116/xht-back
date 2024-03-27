package com.xht.service.exception;

import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2023/12/19  21:20
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    //todo security 让全局异常处理失效


    //自定义异常处理
    @ExceptionHandler(XhtException.class)
    @ResponseBody
    public Result error(XhtException e) {
        return Result.build(null, ResultStatus.FAILURE, e.getCode(),e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return  Result.build(null, ResultStatus.FAILURE, 999,e.getMessage());
    }


}
