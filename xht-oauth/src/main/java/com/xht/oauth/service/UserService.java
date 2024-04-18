package com.xht.oauth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xht.model.dto.oauth.UserDto;
import com.xht.model.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xht
 * @since 2024-03-17
 */
public interface UserService extends IService<User> {

    UserDto getByName(String username);
}
