package com.xht.oauth.component;

import com.alibaba.fastjson2.JSON;

import com.xht.model.constant.RedisKeyConst;
import com.xht.model.entity.User;
import com.xht.model.vo.common.ResultCodeEnum;

import com.xht.common.service.exception.XhtException;
import com.xht.common.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class XhtJwtTokenSecurityFilter extends OncePerRequestFilter {

    private static final String FILTER_APPLIED = XhtJwtTokenSecurityFilter.class.getName() + ".APPLIED";

    private JwtTokenUtil jwtTokenUtil;


    private XhtUserDetailsService userDetailsService;


    private RedisTemplate<String,String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.doFilter(request,response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        if (request.getAttribute(FILTER_APPLIED) != null) {
            chain.doFilter(request, response);
        } else {

            request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
            // 1. 解析请求中的令牌
            String authorization = request.getHeader(jwtTokenUtil.getTokenHead());
            // 2. 令牌不存在，直接进入下一个过滤器
            if (authorization == null) {
                chain.doFilter(request, response);
            } else {
                // 3. 存在令牌
                try {
                    // 3.1 校验
                    authorization = authorization.replace(jwtTokenUtil.getBearer(),"");
                    String userJson = (String) redisTemplate.opsForValue().get(RedisKeyConst.USER_TOKEN_KEY + authorization);
                    User user = JSON.parseObject(userJson, User.class);
                    if (user == null){
                        log.info("XhtJwtTokenSecurityFilter-user : null");
                        throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
                    }
                    boolean validateToken = jwtTokenUtil.validateToken(authorization, user);
                    if (!validateToken){
                        log.info("XhtJwtTokenSecurityFilter-validateToken : false");
                        throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
                    }
                    // 3.3 组装认证信息
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 3.3 保存用户信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("token认证通过");
                    chain.doFilter(request, response);
                }catch (Exception e){
                    throw new XhtException(e.getMessage(),ResultCodeEnum.SYSTEM_ERROR2.getCode());
                }finally{
                    request.removeAttribute(FILTER_APPLIED);
                }
            }
        }
    }
}
