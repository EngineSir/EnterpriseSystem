package io.dtchain.utils;

import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Utils {
	/*
	 * 利用UUID算法生成主键
	 */
	public static String createId() {
		UUID uuid = UUID.randomUUID();
		String id = uuid.toString();
		return id.replace("-", "");
	}
	
	public static String Md5(String userName,String num) {
		return new SimpleHash("MD5", num,  ByteSource.Util.bytes(userName), 2).toHex();
	}
}
