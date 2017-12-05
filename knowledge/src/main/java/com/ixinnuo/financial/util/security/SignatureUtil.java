package com.ixinnuo.financial.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;

public class SignatureUtil {
	
	public static final String charset_UTF8 = "UTF-8";
	
	public static final String Algorithm_DSA = "DSA";
	//私钥的算法是RSA，那么签名就只能使用withRSA类型，同理DSA,ECDSA
	public static final String Algorithm_MD5withRSA = "MD5withRSA";
	public static final String Algorithm_SHA1withRSA = "SHA1withRSA";

	
	public static void main(String[] args) {
		String msg = "hello";
		PrivateKey privateKey = KeyToolUtil.getPrivateKeyFromKeyStore("JKS", "carl.keystore", "qq2476056494", "carl","qq2476056494");
		String sign = signature(msg, privateKey,Algorithm_MD5withRSA);
		System.out.println("私钥签名："+sign);
		PublicKey publicKey = KeyToolUtil.getPublicKeyFromCert(KeyToolUtil.getCert("carl.crt"));
		boolean verify = verify(msg, sign, publicKey, Algorithm_MD5withRSA);
		System.out.println("公钥验证："+verify);
	}
	
	/**
	 * 对字符串签名
	 * @param message 消息字符串，可以是明文或密文
	 * @param privateKey 私钥
	 * @param algorithm 签名算法
	 * @return
	 */
	public static String signature(String message,PrivateKey privateKey,String algorithm){
		try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(privateKey);
			signature.update(message.getBytes(charset_UTF8));
			byte[] sign = signature.sign();
			return Base64.getEncoder().encodeToString(sign);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 公钥验证签名是否一致，判断数据的所有者是否同一人
	 * @param message 消息字符串
	 * @param sign 签名后的字符串
	 * @param publicKey 公钥
	 * @param algorithm 签名算法
	 * @return
	 * @throws Exception
	 */
    public static boolean verify(String message, String sign,PublicKey publicKey,String algorithm){
        try {
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(publicKey);
			signature.update(message.getBytes(charset_UTF8));
			byte[] decode = Base64.getDecoder().decode(sign);
			return signature.verify(decode);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return false;
    }
}
