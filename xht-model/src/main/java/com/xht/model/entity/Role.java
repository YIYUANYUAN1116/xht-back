package com.xht.model.entity;

import com.xht.model.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xht
 * @since 2024-03-19
 */
@Getter
@Setter
public class Role extends BaseEntity {

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
