package com.xht.oauth.service;

import com.xht.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xht
 * @since 2024-03-19
 */
public interface RoleService extends IService<Role> {

    List<Role> getByUserId(Long userId);
}
