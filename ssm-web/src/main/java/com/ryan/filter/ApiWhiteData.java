package com.ryan.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * API白名单列表
 * @author YoriChen
 * @date 2018/5/21
 */
public class ApiWhiteData {
	private static List<String> whiteApis = new ArrayList<String>();
	static {
		whiteApis.add("/json");
	}
	public static List<String> getWhiteApis() {
		return whiteApis;
	}
	
	public static void add(String uri) {
		whiteApis.add(uri);
	}
}
