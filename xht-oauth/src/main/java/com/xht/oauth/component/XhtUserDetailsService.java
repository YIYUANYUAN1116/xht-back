package com.xht.oauth.component;

import com.xht.model.dto.oauth.RoleDto;
import com.xht.model.dto.oauth.UserDto;
import com.xht.oauth.service.RoleService;
import com.xht.oauth.service.UserService;
import com.xht.model.entity.User;
import com.xht.model.entity.Role;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : YIYUANYUAN
 * @date: 2024/3/19  23:08
 */
@Service
public class XhtUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.查询用户
        UserDto userDto = userService.getByName(username);
        if (userDto == null){
            throw new UsernameNotFoundException(username + " can not found !");
        }
        //2.查询用户权限
        List<RoleDto> roleDtoList = userDto.getRoleDtoList();
        List<String> list = roleDtoList.stream().map(RoleDto::getRoleCode).toList();
        List<GrantedAuthority> grantedAuthorityList = AuthorityUtils.createAuthorityList(list);
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        return new XhtUserDetails(user.getId(),user.getUsername(), user.getPassword(), user,grantedAuthorityList);

    }
}
