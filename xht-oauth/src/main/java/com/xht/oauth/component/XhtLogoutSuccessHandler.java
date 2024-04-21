package com.xht.oauth.component;


import com.xht.model.vo.common.ResultStatus;
import com.xht.common.utils.ResponseUtils;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @author : YIYUANYUAN
 * @description : 登出处理器
 * @date: 2023/12/19  20:15
 */
public class XhtLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtils.buildResponse(response, Result.build(ResultStatus.SUCCESS, ResultCodeEnum.SUCCESS),HttpStatus.OK);
    }
}
