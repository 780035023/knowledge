package com.ixinnuo.financial.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


/**
 * 公钥加密，私钥解密
 * 证书算法： x509Certificate.getSigAlgName()  用来加签/验签  keytool -genkeypair -sigalg SHA1withRSA
 * 密钥算法：key.getAlgorithm()  用来加密/解密 创建密钥对的时候指定的  keytool -genkeypair -keyalg RSA
 * @author aisino
 * 参考顺序
 * MessageDigestUtil--》KeyToolUtil--》SignatureUtil--》CipherUtil
 */
public class CipherUtil {
	
	public static final String CHARSET_UTF8 = "UTF-8";
	public static final String Algorithm_AES = "AES";
	public static final String Algorithm_DES = "DES";
	public static final String Algorithm_DESede = "DESede";//也叫3DES
	public static final String Algorithm_RSA = "RSA";
	public static final String Algorithm_PBEWithMD5AndDES = "PBEWithMD5AndDES";

	public static void main(String[] args) {
		String msg = "hello 你好";
		Certificate certificate = KeyToolUtil.getCert("carl.crt");
		String encryptMsg = encryptMsg(msg, certificate);
		System.out.println(certificate.getPublicKey().getAlgorithm()+"加密串：" + encryptMsg);
		PrivateKey privateKey = KeyToolUtil.getPrivateKeyFromKeyStore("JKS", "carl.keystore", "qq2476056494", "carl","qq2476056494");
		String decryptMsg = decryptMsg(encryptMsg, privateKey);
		System.out.println(privateKey.getAlgorithm() + "解密后：" + decryptMsg);
	}
	
	/**
	 * 加密数据，证书里公钥的算法
	 * @param msg 数据
	 * @param certificate 证书（公钥）
	 * @return base64编码处理的字符串
	 */
	public static String encryptMsg(String msg,Certificate certificate){
		try {
			//使用证书公钥的算法，生成密钥对的时候指定的算法
			Cipher instance = Cipher.getInstance(certificate.getPublicKey().getAlgorithm());
			//ENCRYPT_MODE 加密模式
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
	 * @param encryptMsg 加密后字符串，且base64编码处理过
	 * @param privateKey 私钥
	 * @return 解密后的数据
	 */
	public static String decryptMsg(String encryptMsg,PrivateKey privateKey){
		try {
			//使用私钥的算法，生成密钥对的时候指定的算法
			Cipher instance = Cipher.getInstance(privateKey.getAlgorithm());
			//DECRYPT_MODE 解密模式
			instance.init(Cipher.DECRYPT_MODE, privateKey);
			instance.update(Base64.getDecoder().decode(encryptMsg));
			byte[] doFinal = instance.doFinal();
			return new String(doFinal,CHARSET_UTF8);
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
	
	
}
