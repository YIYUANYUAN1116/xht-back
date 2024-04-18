package com.xht.oauth.service.impl;


import com.xht.model.dto.oauth.UserDto;
import com.xht.oauth.mapper.UserMapper;
import com.xht.model.entity.User;
import com.xht.oauth.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xht
 * @since 2024-03-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto getByName(String username) {
        return userMapper.getByName(username);
    }
}
