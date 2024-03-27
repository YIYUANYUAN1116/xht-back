package com.xht.oauth.mapper;

import com.xht.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xht
 * @since 2024-03-19
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getByUserId(@Param("userId") Long userId);
}
