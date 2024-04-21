package com.xht.oauth.component;

import com.xht.model.vo.common.ResultStatus;
import com.xht.common.utils.ResponseUtils;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/17  11:26
 */
public class XhtLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseUtils.buildResponse(response, Result.build(ResultStatus.FAILURE, ResultCodeEnum.LOGIN_ERROR), HttpStatus.OK);

    }
}
