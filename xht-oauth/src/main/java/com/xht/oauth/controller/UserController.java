package com.xht.oauth.controller;

import com.xht.model.entity.User;
import com.xht.model.vo.common.Result;
import com.xht.model.vo.common.ResultCodeEnum;
import com.xht.model.vo.common.ResultStatus;
import com.xht.oauth.component.XhtUserDetails;
import com.xht.oauth.service.UserService;
import com.xht.service.exception.XhtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/25  23:04
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/currentUserInfo")
    public Result currentUserInfo(){
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof XhtUserDetails xhtUserDetails){
            user = xhtUserDetails.getUser();
        }else {
            throw new XhtException("匿名用户",ResultCodeEnum.TOKEN_EXPIRED.getCode());
        }
        return Result.build(user, ResultStatus.SUCCESS, ResultCodeEnum.SUCCESS);
    }
}
