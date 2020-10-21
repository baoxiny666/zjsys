package com.tried.common;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {

	/**
	  * @Description 案例
	  * @author liuxd
	  * @date 2016-3-28 上午11:37:50
	  * @version V1.0
	 */
	public static void main(String[] args) throws Exception {
		String content = "@#!~";
        //--密匙
		String key = ConfigUtils.random32();
        //--加密
		String encrypt = encrypt(content, key);
        //--解密
		String decrypt = decrypt(encrypt, key);
	}

	/**
	 * @Description 根据文件内容和密匙加密
	 * @author liuxd
	 * @date 2016-3-28 上午10:55:54
	 * @version V1.0
	 */
	public static String encrypt(String content, String encryptKey) throws Exception {
		return base64Encode(encryptToBytes(content, encryptKey));
	}

	public static byte[] encryptToBytes(String content, String encryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(encryptKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		return cipher.doFinal(content.getBytes("utf-8"));
	}

	/**
	 * @Description BASE64加密 byte[]转字符串
	 * @author liuxd
	 * @date 2016-3-28 上午10:54:15
	 * @version V1.0
	 */
	public static String base64Encode(byte[] bytes) {
		return new BASE64Encoder().encode(bytes);
	}

	/**
	 * @Description 解密
	 * @author liuxd
	 * @date 2016-3-28 上午10:57:14
	 * @version V1.0
	 */
	public static String decrypt(String encryptStr, String decryptKey) throws Exception {
		return (null == encryptStr || encryptStr.isEmpty()) ? null : decryptByBytes(base64Decode(encryptStr), decryptKey);
	}

	/**
	 * @Description BASE64解密字符串到byte[]
	 * @author liuxd
	 * @date 2016-3-28 上午10:59:41
	 * @version V1.0
	 */
	public static byte[] base64Decode(String base64Code) throws Exception {
		return (null == base64Code || base64Code.isEmpty()) ? null : new BASE64Decoder().decodeBuffer(base64Code);
	}

	public static String decryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128, new SecureRandom(decryptKey.getBytes()));
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		return new String(decryptBytes, "utf-8");
	}

}
