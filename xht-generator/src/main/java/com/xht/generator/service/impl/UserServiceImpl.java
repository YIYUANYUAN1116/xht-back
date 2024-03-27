package com.xht.generator.service.impl;

import com.xht.generator.entity.User;
import com.xht.generator.mapper.UserMapper;
import com.xht.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
