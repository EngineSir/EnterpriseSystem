package com.example.demo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnterpriseSystemApplicationTests {

	@Test
	public void contextLoads() {
		String newPassword = new SimpleHash("MD5", "admin",  ByteSource.Util.bytes("admin"), 2).toHex();
		System.out.println(newPassword);
	}

}

