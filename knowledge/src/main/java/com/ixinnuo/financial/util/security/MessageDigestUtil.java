package com.ixinnuo.financial.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 普通的消息摘要算法的主要特征是加密过程不需要密钥，并且经过加密的数据无法被解密，只有输入相同的明文数据经过相同的消息摘要算法才能得到相同的密文。
 * HMAC是密钥相关的哈希运算消息认证码，HMAC运算利用哈希算法，以一个密钥和一个消息为输入，生成一个消息摘要作为输出。
 * @author aisino
 * 参考顺序
 * MessageDigestUtil--》KeyToolUtil--》SignatureUtil--》CipherUtil
 *
 */
public class MessageDigestUtil {
	
	
	public static final String charset_UTF8 = "UTF-8";
	
	public static final String Algorithm_MD5 = "MD5";
	public static final String Algorithm_SHA1 = "SHA1";
	public static final String Algorithm_HmacMD5 = "HmacMD5";
	public static final String Algorithm_HmacSHA1 = "HmacSHA1";

	public static void main(String[] args) throws Exception {
		//listAlgorithm();
		System.out.println(messageDigest("你好",Algorithm_MD5));
//		System.out.println(messageDigest("你好",Algorithm_SHA1));
//		System.out.println(hmacMessageDigest("我是密钥","你好",Algorithm_HmacMD5));
//		System.out.println(hmacMessageDigest("我是密钥","你好",Algorithm_HmacSHA1));
		
	}
	/**
	 * 普通的消息摘要
	 * @param src
	 * @return 经过十六进制换算处理的字符串<br/>
	 * MD5 :16个长度的字节，即16*8=128位二进制；与十六进制运算得到32位小写字符串,如果需要16位，自行截取8-24，即去掉首位各8位长度<br/>
	 * SHA1: 20个长度的字节，即20*8=160位二进制；与十六进制运算得到40位小写字符串
	 */
	public static String messageDigest(String src,String algorithm) {
		 if (null == src) {
	           return null;
	       }
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			byte[] digest = messageDigest.digest(src.getBytes(charset_UTF8));
			return BytesHexStrTranslate.bytesToHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * hmac带密钥的消息摘要
	 * @param key 密钥，可以是约定好的，或是随机的，双方保持一致即可
	 * @param src 源消息
	 * @param algorithm 算法
	 * @return 经过十六进制换算处理的字符串<br/>
	 * hmacMD5 :16个长度的字节，即16*8=128位二进制；与十六进制运算得到32位小写字符串,如果需要16位，自行截取8-24，即去掉首位各8位长度<br/>
	 * hmacSHA1: 20个长度的字节，即20*8=160位二进制；与十六进制运算得到40位小写字符串
	 */
	public static String hmacMessageDigest(String key,String src,String hamcAlgorithm) {
		 if (null == src) {
	           return null;
	       }
		try {
			//可以是约定好的，或是随机的，双方保持一致即可
			SecretKey secretKey=new SecretKeySpec(key.getBytes(charset_UTF8),hamcAlgorithm);
//			除了上面的指定密钥内容外，也可以随机生成key
//			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
//			SecretKey secretKey2 = keyGenerator.generateKey();
			
			Mac mac=Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			byte[] doFinal = mac.doFinal(src.getBytes(charset_UTF8));
			return BytesHexStrTranslate.bytesToHexString(doFinal);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * 列出当前jvm支持的所有安全算法
	 */
	public static void listAlgorithm() {
		for (Provider provider : Security.getProviders()) {
			System.out.println("Provider: " + provider.getName());
			for (Provider.Service service : provider.getServices()) {
				System.out.println("  Algorithm: " + service.getAlgorithm());
			}
		}

	}
	
	

}
