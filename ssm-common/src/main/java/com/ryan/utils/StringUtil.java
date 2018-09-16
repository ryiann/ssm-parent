package com.ryan.utils;

import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  字符串操作类
 * @author YoriChen
 * @date 2018/5/21
 */
public class StringUtil {

	private StringUtil() {
		// util class, prevent from new instance
	}

	public static String trim(String param) {
		return null == param ? null : param.trim();
	}

	public static String toLowerCase(String param) {
		return null == param ? null : param.toLowerCase();
	}

	/**
	 * 判断一个对象是否是空. 如果是空返回true 如果对象是string类型，调用isEmpty()
	 *
	 * @param object Object
	 * @return boolean
	 */
	public static boolean isNull(final Object object) {
		if (object instanceof String) {
			return StringUtil.isEmpty(object.toString());
		}
		return object == null;
	}

	/**
	 * Checks if string is null or empty.
	 *
	 * @param value The string to be checked
	 * @return True if string is null or empty, otherwise false.
	 */
	public static boolean isEmpty(final String value) {
		return !StringUtils.hasText(value) || "null".equalsIgnoreCase(value);
	}

	/**
	 * 把一个参数变成固定程度，长度不足用0补充. 如果原长度超过要求长度，将原长度直接返回.
	 *
	 * @param value 原参数值
	 * @param len 参数需要的长度.
	 * @return String
	 */
	public static String fillZero(final String value, int len) {
		if (StringUtil.isNull(value) || len <= 0) {
			throw new IllegalArgumentException("参数不正确");
		}
		StringBuffer zero = new StringBuffer();
		StringBuffer sb = new StringBuffer(len);
		int paramLen = value.trim().length();
		if (paramLen < len) {
			for (int i = 0; i < len - paramLen; i++) {
				zero.append("0");
			}
		}
		return sb.append(zero).append(value).toString();
	}

	/**
	 * 返回对数据位的校验.
	 *
	 * @param sourceValue String
	 * @return int
	 */
	public static int getValiateCode(final String sourceValue) {
		int validateCode = 0;
		assert sourceValue != null;
		for (int i = 0, len = sourceValue.length(); i < len; i++) {
			int value = validateCode + (sourceValue.charAt(i) - 48);
			validateCode = value - value / 10 * 10;
		}
		return validateCode;
	}

	/**
	 * Converts <code>null</code> to empty string, otherwise returns it
	 * directly.
	 *
	 * @param obj The nullable string
	 * @return empty string if passed in string is null, or original string
	 * without any change
	 */
	public static String null2String(Object obj) {
		return obj == null ? "" : obj.toString();
	}

	public static String arrayToSQLString(Object[] params) {
		StringBuffer sb = new StringBuffer();
		if (null == params) {
			return null;
		}
		for (int i = 0, len = params.length; i < len; i++) {
			if (i == 0) {
				sb.append("(");
				sb.append("'").append(params[i]).append("'");
			} else {
				sb.append(",'").append(params[i]).append("'");
			}
			if (i == len - 1) {
				sb.append(")");
			}
		}
		return sb.toString();
	}

	public static String arrayToSQLParamStr(Object[] params) {
		StringBuilder sb = new StringBuilder();
		if (null == params) {
			return null;
		}
		for (int i = 0, len = params.length; i < len; i++) {
			if (i == 0) {
				sb.append("(");
				sb.append("?");
			} else {
				sb.append(",?");
			}
			if (i == len - 1) {
				sb.append(")");
			}
		}
		return sb.toString();
	}

	public static String arrayToString(Object[] params) {
		StringBuffer sb = new StringBuffer();
		if (null == params) {
			return null;
		}
		for (int i = 0, len = params.length; i < len; i++) {
			if (i == 0) {
				sb.append(params[i]);
			} else {
				sb.append(",").append(params[i]);
			}

		}
		return sb.toString();
	}

	public static String arrayToSumString(Object[] params) {
		StringBuffer sb = new StringBuffer();
		if (null == params) {
			return null;
		}
		for (int i = 0, len = params.length; i < len; i++) {
			if (i == 0) {
				sb.append("sum(").append(params[i]).append(")");
			} else {
				sb.append(",sum(").append(params[i]).append(")");
			}

		}
		return sb.toString();
	}

	/**
	 * 将带有固定分隔符的字符串转换成List,对字符串中每个值trim，忽略长度==0的字符串.
	 *
	 * @param str 待转换的字符串
	 * @param delimiters 分隔符
	 * @return
	 */
	public static List tokenizeToStringList(String str, String delimiters) {
		return StringUtil.tokenizeToStringList(str, delimiters, true, true);
	}

	/**
	 * 将带有固定分隔符的字符串转换成List.
	 *
	 * @param str 待转换的字符串
	 * @param delimiters 分隔符
	 * @param trimTokens 是否对字符串trim
	 * @param ignoreEmptyTokens 是否忽略长度==0的字符串
	 * @return List
	 */
	public static List tokenizeToStringList(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		if (str == null) {
			return null;
		}

		StringTokenizer st = new StringTokenizer(str, delimiters, false);
		List tokens = new ArrayList();

		boolean preIsDelimiters = false;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (token.equals(delimiters)) {
				if (preIsDelimiters) {
					if (!ignoreEmptyTokens) {
						tokens.add("");
						continue;
					}
				} else {
					preIsDelimiters = true;
					continue;
				}
			} else {
				preIsDelimiters = false;
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return tokens;
	}

	/**
	 * 比较两个字符串是否相等. 如果都是空相等.
	 *
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static Boolean isEqual(String value1, String value2) {
		if (isEmpty(value1) && isEmpty(value2)) {
			return true;
		}
		if (!isEmpty(value1)) {
			return value1.equals(value2);
		}
		if (!isEmpty(value2)) {
			return value2.equals(value1);
		}

		return false;
	}

	public static int compareTo(String value1, String value2) {
		if (isEmpty(value1) && isEmpty(value2)) {
			return 0;
		}
		if (isEmpty(value1)) {
			return -1;
		}
		if (isEmpty(value2)) {
			return 1;
		}

		return value1.compareTo(value2);
	}

	public static String getFixLenString(String s, int width) {
		byte[] by = s.getBytes();

		if (by.length > width) {
			return new String(by, 0, width);
		} else if (by.length == width) {
			return s;
		} else {
			byte[] dest = new byte[width];

			for (int i = 0; i < by.length; i++) {
				dest[i] = by[i];
			}

			for (int i = by.length; i < width; i++) {
				dest[i] = 0x20;
			}

			return new String(dest);
		}
	}

	/**
	 * 检查字符串s是否在字符串数组array中
	 *
	 * @param s
	 * @param array
	 * @return
	 */
	public static boolean isContained(String s, String[] array) {
		if (s == null) {
			return false;
		} else {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					continue;
				} else if (s.compareTo(array[i]) == 0) {
					return true;
				}
			}

			return false;
		}
	}

	/**
	 * 判断一个字符串是否含有中文
	 *
	 * @param str
	 * @return true 含有中文 false 不含
	 * */
	public static boolean isHaveHan(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是不是数字
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 检查数字n是否在数字数组array中
	 *
	 * @param n
	 * @param array
	 * @return
	 */
	public static boolean isContained(int n, int[] array) {
		for (int i = 0; i < array.length; i++) {
			if (n == array[i]) {
				return true;
			}
		}

		return false;
	}

	/**
	 * MD5对字符串加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			}
			else{
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}

		return md5StrBuff.toString();
	}

	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 *
	 * @param value 指定的字符串
	 * @return 字符串的长度
	 */
	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取一个字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字符 */
			if (temp.matches(chinese)) {
				/* 中文字符长度为2 */
				valueLength += 2;
			} else {
				/* 其他字符长度为1 */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 判断是否是基本类型或者是非howbuy定义类型.
	 * 
	 * @param value
	 * @return 是true
	 */
	protected static boolean isBaseTypeClass(Object value) {
		if (null == value || value instanceof Integer || value instanceof String
				|| value instanceof Double || value instanceof Float
				|| value instanceof Byte || value instanceof Boolean
				|| value instanceof Long || value instanceof Character) {
			return true;
		}
		return false;
	}

	static class Test {
		String a;
		String b;

		public Test(String a, String b) {
			super();
			this.a = a;
			this.b = b;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

	}

	public static boolean isBlank(CharSequence str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 字符处理 保留二位小数点
	 *
	 * @param str
	 * @return
	 */
	public static String point(String str) {
		
		 int  donetInt =str.indexOf(".");
         StringBuffer sb = new StringBuffer(str);
         if(donetInt>-1){
         	int donelength = str.substring(donetInt+1).length();
         	int intValue = 2-donelength;
         	if(intValue>0 && intValue==1){
         		sb.append("0");
         	}else if(intValue>0 && intValue==2){
         		sb.append("00");
         	}
         }else{
         	sb.append(".00");
         }
		
		return sb.toString();
	}
	
	
	public static boolean isNotBlank(CharSequence str) {
		return !isBlank(str);
	}

	/**
	 * 数字加1，不够位数的在前面补0，保留len的长度位数字
	 * 
	 * @param digit 需要计算的数字，只能是数字
	 * @param len 保留len的位数
	 * @author 000001976
	 * @date 2017-09-26
	 * @return digit数字加1后，位数不够时补0的字符串
	 */
	public static String calDigitPlusOne(String digit, int len) {
        String result = "";
		// 0 代表前面补充0
		// len 代表长度
        // d 代表参数为正数型 
		result = String.format("%0" + len + "d", Integer.parseInt(digit) + 1);
        return result;
    }

}