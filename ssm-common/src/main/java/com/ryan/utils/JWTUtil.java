package com.ryan.utils;

import com.ryan.constant.Constants;
import com.ryan.enums.ResponseCode;
import com.ryan.pojo.ResponseEntity;
import io.jsonwebtoken.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * API调用认证工具类，采用RSA加密
 * @author YoriChen
 * @date 2018/5/21
 */
public class JWTUtil {
	
	private static RSAPrivateKey priKey;
	private static RSAPublicKey pubKey;
	
	static {
		String modulus = "120749774428185480467622030722535804071445078993623309709775427878906293937338059423076695854937532244466465395164541641368876529295825453848760144835049363522545908104302024165873971414491110512342791594610742544380402908598585190494003507524195754273822268813447403290817343077571516216147839402414940310061";
		String publicExponent = "65537";
		String privateExponent = "73923469942286919561803730971048133587965873619209827001168953680477872428610977313161774128992838682156392947263251899461404460204267953359475632559803617319478756560848229397545070273747796303141458040475889804016062973476403760709402857872540300632704514872361476749953797952016730690123983122643596231873";
		// 私钥
		priKey = RSAUtil.getPrivateKey(modulus, privateExponent);
		// 公钥
		pubKey = RSAUtil.getPublicKey(modulus, publicExponent);
	}
	
	/**
	 * 获取Token
	 *
	 * @param uid 用户ID
	 * @param exp 失效时间，单位分钟
	 * @return
	 */
	public static String getToken(String uid, int exp) {
		long endTime = System.currentTimeMillis() + 1000 * 60 * exp;
		return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
				.signWith(SignatureAlgorithm.RS512, priKey).compact();
	}

	/**
	 * 获取Token
	 *
	 * @param uid 用户ID
	 * @return
	 */
	public static String getToken(String uid) {
		long endTime = System.currentTimeMillis() + 1000 * 60 * 1440;
		return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
				.signWith(SignatureAlgorithm.RS512, priKey).compact();
	}
	
	/**
	 * 设置token当前时间失效
	 *
	 * @param uid
	 * @return
	 */
	public static String setTokenExpiration(String uid) {
		long endTime = System.currentTimeMillis() - 1000 * 60;
		return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
				.signWith(SignatureAlgorithm.RS512, priKey).compact();
	}

	/**
	 * 检查Token是否合法
	 *
	 * @param token
	 * @return JWTResult
	 */
	@SuppressWarnings("rawtypes")
	public static ResponseEntity checkToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
			String sub = claims.get("sub", String.class);
			return new ResponseEntity(Constants.RESPONSE_SUCCESS, "请求成功");
		} catch (ExpiredJwtException e) {
			// 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
            return new ResponseEntity(ResponseCode.TOKEN_EXPIRED.getCode(), ResponseCode.TOKEN_EXPIRED.getMessage());
		} catch (SignatureException e) {
			// 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return new ResponseEntity(ResponseCode.TOKEN_INVALID_REQUEST.getCode(), ResponseCode.TOKEN_INVALID_REQUEST.getMessage());
		} catch (Exception e) {
            return new ResponseEntity(ResponseCode.TOKEN_INVALID_REQUEST.getCode(), ResponseCode.TOKEN_INVALID_REQUEST.getMessage());
		}
	}
}