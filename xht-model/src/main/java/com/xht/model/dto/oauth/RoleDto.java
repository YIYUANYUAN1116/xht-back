package com.xht.model.dto.oauth;

import com.xht.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/28  22:21
 */

@Data
public class RoleDto extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
}
