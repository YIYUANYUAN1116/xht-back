package com.xht.security.interceptor;

import com.alibaba.fastjson.JSON;

import com.xht.model.constant.RedisKeyConst;
import com.xht.model.vo.common.ResultCodeEnum;
import com.xht.security.common.service.exception.XhtException;
import com.xht.security.common.utils.JwtTokenUtil;
import com.xht.security.component.XhtUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/22  22:59
 */
@Component
@Slf4j
public class XhtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        // 1. 解析请求中的令牌
        String authorization = request.getHeader(jwtTokenUtil.getTokenHead());
        // 2. 令牌不存在，直接进入下一个过滤器
        if (authorization == null) {
            throw new XhtException(ResultCodeEnum.NOT_AUTHENTICATION);
        } else {
            // 3. 存在令牌
            try {
                // 3.1 校验
                authorization = authorization.replace(jwtTokenUtil.getBearer(),"");
                String userJson = (String) redisTemplate.opsForValue().get(RedisKeyConst.USER_TOKEN_KEY + authorization);
                XhtUserDetails xhtUserDetails = JSON.parseObject(userJson, XhtUserDetails.class);
                if (xhtUserDetails == null) {
                    log.info("XhtJwtTokenSecurityFilter-user : null");
                    throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(xhtUserDetails, null, xhtUserDetails.getAuthorities());
                // 3.3 保存用户信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }catch (Exception e){
                throw new XhtException(e.getMessage(),null);
            }
        }
    }
}
