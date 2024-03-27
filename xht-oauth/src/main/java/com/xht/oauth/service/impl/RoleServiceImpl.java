package com.xht.oauth.service.impl;

import com.xht.model.entity.Role;
import com.xht.oauth.mapper.RoleMapper;
import com.xht.oauth.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xht
 * @since 2024-03-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getByUserId(Long userId) {
        return roleMapper.getByUserId(userId);
    }
}
