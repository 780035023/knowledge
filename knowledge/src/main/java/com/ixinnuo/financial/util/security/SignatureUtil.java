package com.ixinnuo.financial.util.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
/**
 * 数字签名算法可以看做是一种带有密钥的消息摘要算法，并且这种密钥包含了公钥和私钥。
 * 也就是说，数字签名算法是非对称加密算法和消息摘要算法的结合体。
 * 证书算法： x509Certificate.getSigAlgName()  用来加签/验签  keytool -genkeypair -sigalg SHA1withRSA
 * 密钥算法：key.getAlgorithm()  用来加密/解密 创建密钥对的时候指定的  keytool -genkeypair -keyalg RSA
 * @author aisino
 * 参考顺序
 * MessageDigestUtil--》KeyToolUtil--》SignatureUtil--》CipherUtil
 */
public class SignatureUtil {
	
	public static final String charset_UTF8 = "UTF-8";
	
	//私钥的算法是RSA，那么签名就只能使用withRSA类型，同理DSA,ECDSA
	public static final String Algorithm_MD5withRSA = "MD5withRSA";
	public static final String Algorithm_SHA1withRSA = "SHA1withRSA";

	
	public static void main(String[] args) {
		String msg = "hello 你好";
		String sign = signature(msg, "JKS", "carl.keystore", "qq2476056494", "carl","qq2476056494");
		System.out.println("私钥签名："+sign);
		X509Certificate cert = (X509Certificate) KeyToolUtil.getCert("carl.crt");
		boolean verify = verify(msg, sign, cert);
		System.out.println("公钥验证："+verify);
	}
	
	/**
	 * 对字符串签名，需要从密钥库获取证书的签名算法，以及密钥来签名
	 * @param message 消息字符串，可以是明文或密文
	 * @param keyStoreType 密钥库类型 keytool -list可以查看
	 * @param keyStorePath 密钥库的文件路径
	 * @param keyStorePass 密钥库密码
	 * @param alias 条目别名
	 * @param aliasPass 条目密码
	 * @return base64编码处理的字符串
	 */
	public static String signature(String message,String keyStoreType,String keyStorePath,String keyStorePass,String alias,String aliasPass){
		try {
			//因为生成证书的类型为JKS 也有其他的格式  -list可以查看密钥库的条目类型
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);  
			//读取keystore文件转换为keystore密钥库对象  
			FileInputStream fis = new FileInputStream(keyStorePath);  
			//该密钥库的密码
			keyStore.load(fis, keyStorePass.toCharArray());  
			fis.close();  
			//从密钥库获取证书的签名算法
			X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
			Signature signature = Signature.getInstance(certificate.getSigAlgName());
			//从密钥库获取私钥
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, aliasPass.toCharArray());
			//初始化签名私钥
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
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 公钥验证签名是否一致，判断数据的所有者是否同一人；将签名sign进行base64解码位字节后进行验签
	 * @param message 未签名的消息字符串
	 * @param sign 签名
	 * @param certificate 证书
	 * @return true 验签成功，false验签失败
	 */
    public static boolean verify(String message, String sign,X509Certificate certificate){
        try {
			Signature signature = Signature.getInstance(certificate.getSigAlgName());
			//初始化验签公钥
			signature.initVerify(certificate);
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
