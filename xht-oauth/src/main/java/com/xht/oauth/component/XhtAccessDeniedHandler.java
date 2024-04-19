package com.xht.oauth.component;

import com.xht.common.utils.ResponseUtils;
import com.xht.model.vo.common.ResultStatus;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
* @Description: 自定义无权限拒绝处理器，security默认为重定向403
* @Param:
* @return:
* @Author: yzd
* @Date: 2023/12/20-9:11
*/
public class XhtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        ResponseUtils.buildResponse(response, Result.build(ResultStatus.FAILURE, ResultCodeEnum.ACCESS_DENIED), HttpStatus.OK);
    }
}
