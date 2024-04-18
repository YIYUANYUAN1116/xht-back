package com.xht.model.dto.oauth;

import com.xht.model.entity.Role;
import com.xht.model.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/28  22:19
 */

@Data
public class UserDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * @Author: yzd
     * @Date: 2024/3/28
     */
    private List<RoleDto> roleDtoList;
}
