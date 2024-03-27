package com.xht.oauth.component;

import com.xht.model.vo.common.ResultStatus;
import com.xht.utils.ResponseUtils;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
* @Description: 自定义未登录拒绝处理器，security默认为重定向默认登录页
* @Param: 
* @return:
* @Author: yzd
* @Date: 2023/12/20-9:13
*/

public class XhtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

        ResponseUtils.buildResponse(response, Result.build(ResultStatus.FAILURE, ResultCodeEnum.NOT_AUTHENTICATION), HttpStatus.OK);
    }
}
