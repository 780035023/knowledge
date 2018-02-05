package com.ixinnuo.financial.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 公钥加密，私钥解密 证书算法： x509Certificate.getSigAlgName() 用来加签/验签 keytool -genkeypair
 * -sigalg SHA1withRSA 密钥算法：key.getAlgorithm() 用来加密/解密 创建密钥对的时候指定的 keytool
 * -genkeypair -keyalg RSA
 * 
 * @author aisino 参考顺序
 *         MessageDigestUtil--》KeyToolUtil--》SignatureUtil--》CipherUtil
 */
public class CipherUtil {

	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String Algorithm_AES = "AES";
	public static final String Algorithm_DES = "DES";
	public static final String Algorithm_DESede = "DESede";// 也叫3DES/模式/填充，默认等同于DESede/ECB/PKCS5Padding
	public static final String Algorithm_RSA = "RSA";
	public static final String Algorithm_PBEWithMD5AndDES = "PBEWithMD5AndDES";

	public static void main(String[] args) {
		String msg = "你好hello12a c";
		Certificate certificate = KeyToolUtil.getCert("carl.crt");
		String encryptMsg = encryptMsg(msg, certificate);
		System.out.println(certificate.getPublicKey().getAlgorithm() + "加密串：" + encryptMsg);
		PrivateKey privateKey = KeyToolUtil.getPrivateKeyFromKeyStore("JKS", "carl.keystore", "qq2476056494", "carl",
				"qq2476056494");
		String decryptMsg = decryptMsg(encryptMsg, privateKey);
		System.out.println(privateKey.getAlgorithm() + "解密后：" + decryptMsg);
		System.out.println("==============以下是采用密钥串来加解密的===================");
		// 3des的密钥串只能是24个字节的长度，采用utf编码则24个字母数字，或者8个中文
		String key = "abcdefghijklmnopqrstuvwx";
		String encryptMsg2 = encryptMsg(key, Algorithm_DESede, msg);
		System.out.println("\n" + Algorithm_DESede + "加密串：" + encryptMsg2);
		String decryptMsg2 = decryptMsg(key, Algorithm_DESede, encryptMsg2);
		System.out.println("\n" + Algorithm_DESede + "解密后：" + decryptMsg2);
		System.out.println("==============以下是3des/ecb/zeroPadding加解密的===================");
		String deskey = "111111111111111111111111";
		String encryptWithDESCEBZeroPading = encryptWithDESCEBZeroPading(deskey, "1234567890");
		System.out.println("\n加密串" + encryptWithDESCEBZeroPading);
		String decryptWithDESCEBZeroPading = decryptWithDESCEBZeroPading(deskey, encryptWithDESCEBZeroPading);
		System.out.println("\n解密串" + decryptWithDESCEBZeroPading);
	}

	/**
	 * 加密数据，证书里公钥的算法
	 * 
	 * @param msg
	 *            数据
	 * @param certificate
	 *            证书（公钥）
	 * @return base64编码处理的字符串
	 */
	public static String encryptMsg(String msg, Certificate certificate) {
		try {
			// 使用证书公钥的算法，生成密钥对的时候指定的算法
			Cipher instance = Cipher.getInstance(certificate.getPublicKey().getAlgorithm());
			// ENCRYPT_MODE 加密模式
			instance.init(Cipher.ENCRYPT_MODE, certificate);
			instance.update(msg.getBytes(CHARSET_UTF8));
			byte[] doFinal = instance.doFinal();
			return Base64.getEncoder().encodeToString(doFinal);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密数据，私钥的算法
	 * 
	 * @param encryptMsg
	 *            加密后字符串，且base64编码处理过
	 * @param privateKey
	 *            私钥
	 * @return 解密后的数据
	 */
	public static String decryptMsg(String encryptMsg, PrivateKey privateKey) {
		try {
			// 使用私钥的算法，生成密钥对的时候指定的算法
			Cipher instance = Cipher.getInstance(privateKey.getAlgorithm());
			// DECRYPT_MODE 解密模式
			instance.init(Cipher.DECRYPT_MODE, privateKey);
			instance.update(Base64.getDecoder().decode(encryptMsg));
			byte[] doFinal = instance.doFinal();
			return new String(doFinal, CHARSET_UTF8);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 没有证书的情况下，采用指定密钥串加密
	 * 
	 * @param key
	 *            密钥
	 * @param Algorithm
	 *            加密算法
	 * @param msg
	 *            消息
	 * @return Base64编码后的字符串
	 */
	public static String encryptMsg(String key, String Algorithm, String msg) {
		try {
			// 生成密钥
			byte[] bytes = key.getBytes(CHARSET_UTF8);
			System.out.println("密钥字节长度：" + bytes.length);
			SecretKey deskey = new SecretKeySpec(bytes, Algorithm);
			// 加密工具
			Cipher c1 = Cipher.getInstance(Algorithm);
			// 加密
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] msgBytes = msg.getBytes(CHARSET_UTF8);
			System.out.println("原数据字节如下：");
			for (int i = 0; i < msgBytes.length; i++) {
				System.out.print(msgBytes[i] + "\t");
			}
			//FIXME 此处不能使用update
			byte[] doFinal = c1.doFinal(msgBytes);
			System.out.println("\n加密后的字节如下：");
			for (int i = 0; i < doFinal.length; i++) {
				System.out.print(doFinal[i] + "\t");
			}
			return Base64.getEncoder().encodeToString(doFinal);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 3des ecb 0填充，利用NoPading，自行补0
	 * @param key 密钥串，必须为24长度字符串
	 * @param msg 消息
	 * @return Base64编码后的字符串
	 */
	public static String encryptWithDESCEBZeroPading(String key,String msg) {
		try {
			// 生成密钥
			byte[] bytes = key.getBytes(CHARSET_UTF8);
			System.out.println("密钥字节长度：" + bytes.length);
			SecretKey deskey = new SecretKeySpec(bytes, "DESede");
			// 加密工具
			Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
			// 加密
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] msgBytes = msg.getBytes(CHARSET_UTF8);
			System.out.println("原数据字节如下：");
			for (int i = 0; i < msgBytes.length; i++) {
				System.out.print(msgBytes[i] + "\t");
			}
			//FIXME 自行补位，达到8字节的倍数
			int remainder = msgBytes.length % 8;
			if(0 != remainder){
				int oldLength = msgBytes.length;
				//1.扩展自身长度
				msgBytes = Arrays.copyOf(msgBytes, msgBytes.length + 8-remainder);
				//2.填充扩展内容为0
				Arrays.fill(msgBytes, oldLength, msgBytes.length, (byte)0);
				
			}
			//FIXME 此处不能使用update,自行补位，
			byte[] doFinal = c1.doFinal(msgBytes);
			System.out.println("\n加密后的字节如下：");
			for (int i = 0; i < doFinal.length; i++) {
				System.out.print(doFinal[i] + "\t");
			}
			return Base64.getEncoder().encodeToString(doFinal);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 没有证书的情况下，使用密钥串解密
	 * 
	 * @param key
	 *            密钥串
	 * @param Algorithm
	 *            算法
	 * @param encryptMsg
	 *            已加密的字节经base64编码后的字符串
	 * @return 原始数据，utf-8编码
	 */
	public static String decryptMsg(String key, String Algorithm, String encryptMsg) {
		try {
			// 生成密钥
			byte[] bytes = key.getBytes(CHARSET_UTF8);
			System.out.println("密钥字节长度：" + bytes.length);
			SecretKey deskey = new SecretKeySpec(bytes, Algorithm);
			//初始工具
			Cipher instance = Cipher.getInstance(Algorithm);
			// DECRYPT_MODE 解密模式
			instance.init(Cipher.DECRYPT_MODE, deskey);
			byte[] encryptMsgBytes = Base64.getDecoder().decode(encryptMsg);
			System.out.println("待解密的字节如下：");
			for (int i = 0; i < encryptMsgBytes.length; i++) {
				System.out.print(encryptMsgBytes[i] + "\t");
			}
			//FIXME 此处不能使用update
			byte[] doFinal = instance.doFinal(encryptMsgBytes);
			System.out.println("\n解密后的字节为如下：");
			for (int i = 0; i < doFinal.length; i++) {
				System.out.print(doFinal[i] + "\t");
			}
			return new String(doFinal, CHARSET_UTF8);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 3des ecb 0填充，解密；利用NoPading，自行去除填充的0
	 * @param key 密钥字符串，必须是24长度
	 * @param encryptMsg base64的密文
	 * @return
	 */
	public static String decryptWithDESCEBZeroPading(String key,String encryptMsg) {
		try {
			// 生成密钥
			byte[] bytes = key.getBytes(CHARSET_UTF8);
			System.out.println("密钥字节长度：" + bytes.length);
			SecretKey deskey = new SecretKeySpec(bytes, "DESede");
			//初始工具
			Cipher instance = Cipher.getInstance("DESede/ECB/NoPadding");
			// DECRYPT_MODE 解密模式
			instance.init(Cipher.DECRYPT_MODE, deskey);
			byte[] encryptMsgBytes = Base64.getDecoder().decode(encryptMsg);
			System.out.println("待解密的字节如下：");
			for (int i = 0; i < encryptMsgBytes.length; i++) {
				System.out.print(encryptMsgBytes[i] + "\t");
			}
			//FIXME 此处不能使用update
			byte[] doFinal = instance.doFinal(encryptMsgBytes);
			//去除填充的0,倒数第一个不为0的位置，copy到另一个数组
			int zeroIndex = doFinal.length;
			for (int i = doFinal.length-1; i > 0; i--) {
				if(doFinal[i] == (byte)0){
					zeroIndex = i;
				}else{
					break;
				}
			}
			doFinal = Arrays.copyOf(doFinal, zeroIndex);
			System.out.println("\n解密后的字节为如下：");
			for (int i = 0; i < doFinal.length; i++) {
				System.out.print(doFinal[i] + "\t");
			}
			return new String(doFinal, CHARSET_UTF8);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
