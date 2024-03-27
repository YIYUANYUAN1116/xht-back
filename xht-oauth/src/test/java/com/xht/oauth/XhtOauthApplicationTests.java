package com.xht.oauth;

import com.xht.oauth.mapper.UserMapper;
import com.xht.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class XhtOauthApplicationTests {

	@Autowired
	private UserMapper userService;

	@Test
	void contextLoads() {
		User user = new User();
		user.setUsername("admin");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		user.setPassword(encode);
		user.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
		userService.insert(user);
	}

	@Test
	void contextLoads32222() {
		User user = new User();
		user.setUsername("admin");
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode("123456");
		user.setPassword(encode);
		user.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
		userService.insert(user);
	}

}
