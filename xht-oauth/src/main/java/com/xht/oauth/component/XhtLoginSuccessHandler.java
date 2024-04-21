package com.xht.oauth.component;

import com.alibaba.fastjson2.JSON;
import com.xht.model.constant.RedisKeyConst;
import com.xht.model.vo.common.ResultStatus;
import com.xht.common.utils.JwtTokenUtil;
import com.xht.common.utils.ResponseUtils;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/17  11:23
 */

public class XhtLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedisTemplate redisTemplate;

    private JwtTokenUtil jwtTokenUtil;

    public XhtLoginSuccessHandler() {

    }

    public XhtLoginSuccessHandler(RedisTemplate redisTemplate,JwtTokenUtil jwtTokenUtil){
        this.redisTemplate = redisTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        XhtUserDetails xhtUserDetails = (XhtUserDetails) authentication.getPrincipal();
        String token = null;
        try {
            token = jwtTokenUtil.generateToken(xhtUserDetails.getUser());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        redisTemplate.opsForValue().set(RedisKeyConst.USER_TOKEN_KEY+token, JSON.toJSONString(xhtUserDetails.getUser()),7, TimeUnit.DAYS);
        ResponseUtils.buildResponse(response, Result.build(token, ResultStatus.SUCCESS, ResultCodeEnum.AUTHENTICATION_SUCCESS), HttpStatus.OK);
    }
}
