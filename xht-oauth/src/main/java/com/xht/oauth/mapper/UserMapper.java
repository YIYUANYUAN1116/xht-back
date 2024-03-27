package com.xht.oauth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xht.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xht
 * @since 2024-03-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User getByName(@Param("username") String username);
}
