package com.xht.security.common.utils;


import cn.hutool.system.UserInfo;
import com.xht.model.entity.User;
import com.xht.security.component.XhtUserDetails;
import lombok.Data;

@Data
public class AuthContextUtil {
    public static XhtUserDetails xhtUserDetails;
    public static User user;
}
