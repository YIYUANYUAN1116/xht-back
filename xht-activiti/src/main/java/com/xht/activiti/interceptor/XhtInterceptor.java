package com.xht.activiti.interceptor;

import com.alibaba.fastjson2.JSON;
import com.xht.common.service.exception.XhtException;
import com.xht.common.utils.JwtTokenUtil;
import com.xht.model.constant.RedisKeyConst;
import com.xht.model.entity.User;
import com.xht.model.vo.common.ResultCodeEnum;
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
            return false;
        } else {
            // 3. 存在令牌
            try {
                // 3.1 校验
                authorization = authorization.replace(jwtTokenUtil.getBearer(), "");
                String userJson = (String) redisTemplate.opsForValue().get(RedisKeyConst.USER_TOKEN_KEY + authorization);
                User user = JSON.parseObject(userJson, User.class);
                if (user == null) {
                    log.info("XhtJwtTokenSecurityFilter-user : null");
                    throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
                }
                boolean validateToken = jwtTokenUtil.validateToken(authorization, user);
                if (!validateToken) {
                    log.info("XhtJwtTokenSecurityFilter-validateToken : false");
                    throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
                }
                // 3.3 组装认证信息
//                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                // 3.3 保存用户信息
//                SecurityContextHolder.getContext().setAuthentication(authentication);


                return HandlerInterceptor.super.preHandle(request, response, handler);
            }catch (Exception e){
                throw new XhtException(e.getMessage(),null);
            }
        }
    }
}
