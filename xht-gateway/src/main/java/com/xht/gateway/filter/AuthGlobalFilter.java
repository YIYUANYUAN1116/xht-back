package com.xht.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xht.gateway.properties.XhtGateWayProperties;
import com.xht.model.constant.RedisKeyConst;
import com.xht.model.entity.User;
import com.xht.model.vo.common.ResultCodeEnum;
import com.xht.service.exception.XhtException;
import com.xht.utils.JwtTokenUtil;
import com.xht.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/26  21:25
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private XhtGateWayProperties xhtGateWayProperties;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authorization = null;
        //获取请求路径
        String requestPath = exchange.getRequest().getPath().pathWithinApplication().value();
        log.info("AuthGlobalFilter-requestPath : "+requestPath);
        List<String> ignoreUrl = xhtGateWayProperties.getIgnoreUrl();
        boolean match = ignoreUrl.stream()
                .map(url -> url.replace("/**", ""))
                .anyMatch(requestPath::startsWith);
        if (match){ //匹配放行路径则放行
            log.info("AuthGlobalFilter-match : "+match);
            return chain.filter(exchange);
        }
        //获取token
        authorization = exchange.getRequest().getHeaders().getFirst(jwtTokenUtil.getTokenHead());
        if (StringUtils.hasLength(authorization)){
            String token = authorization.replace(jwtTokenUtil.getBearer(),"");
            String userJson = (String) redisTemplate.opsForValue().get(RedisKeyConst.USER_TOKEN_KEY + token);
            User user = JSON.parseObject(userJson, User.class);
            if (user == null){
                log.info("AuthGlobalFilter-user : null");
                throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
            }
            boolean validateToken = jwtTokenUtil.validateToken(token, user);
            if (!validateToken){
                log.info("AuthGlobalFilter-validateToken : "+validateToken);
                throw new XhtException(ResultCodeEnum.TOKEN_EXPIRED);
            }
        }else {
            log.info("AuthGlobalFilter-authorization : null");
            throw new XhtException(ResultCodeEnum.NOT_AUTHENTICATION);
        }
        exchange.getResponse().getHeaders().add(jwtTokenUtil.getTokenHead(),authorization);
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> buildResponse(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String result = "";
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("code", HttpStatus.UNAUTHORIZED.value());
            map.put("message", "当前请求未认证，不允许访问");
            map.put("data", null);
            result = objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(buffer));
    }
}
