package io.dtchain.utils;

import java.util.UUID;

public class Utils
{
	/*
	 * 利用UUID算法生成主键
	 */
	public static String createId(){
		UUID uuid=UUID.randomUUID();
		String id=uuid.toString();
		return id.replace("-","");
	}
}
