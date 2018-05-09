package com.ixinnuo.financial.util.security;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
/**
 * 数字签名算法可以看做是一种带有密钥的消息摘要算法，并且这种密钥包含了公钥和私钥。<br>
 * 也就是说，数字签名算法是非对称加密算法和消息摘要算法的结合体。<br>
 * 证书算法： x509Certificate.getSigAlgName()  用来加签/验签  keytool -genkeypair -sigalg SHA1withRSA<br>
 * 密钥算法：key.getAlgorithm()  用来加密/解密 创建密钥对的时候指定的  keytool -genkeypair -keyalg RSA<br>
 * @author aisino
 * 参考顺序
 * MessageDigestUtil--》KeyToolUtil--》SignatureUtil--》CipherUtil
 */
public class SignatureUtil {
	
	public static final String charset_UTF8 = "UTF-8";
	
	//私钥的算法是RSA，那么签名就只能使用withRSA类型，同理DSA,ECDSA
	public static final String Algorithm_MD5withRSA = "MD5withRSA";
	public static final String Algorithm_SHA1withRSA = "SHA1withRSA";
	public static final String Algorithm_SHA256withRSA = "SHA256withRSA";
	public static final String Algorithm_RSA = "RSA";

	
	public static void main(String[] args) {
		String msg = "hello 你好";
		String sign = signature(msg, "JKS", "carl.keystore", "qq2476056494", "carl","qq2476056494");
		System.out.println("私钥签名："+sign);
		X509Certificate cert = (X509Certificate) KeyToolUtil.getCert("carl.crt");
		boolean verify = verify(msg, sign, cert);
		System.out.println("公钥验证："+verify);
		System.out.println("===============以下使用密钥串加签验签====================");
		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIwYhNP4/TM1BMX3w+AkWqH/PcV99zk4i8LYbPGqjPS8Mh57Gxp96oZRkJbrD+OBLZKd+agUc+Nppz31+aMjyEfVDCY65eZ41CELmOZRoacsrxiSwOqTqJk9WcBA8d36sPPw6tlT57cGbxEql91P43sehF1A5GvSNy7DY895585RAgMBAAECgYB8Pi5mctXSeP2fuAitmnAKZPtaT0lWJNDF3vxXImLZlKF6KMEzsGGx91ocjaDMqb5J3fIP3vubvhJEwAVBajVuLaFgXPHnExo00lssk/iyHydmuZMg/96nuA4oGcK9kz5IsNdq64BACez+ODnBxDLfkpu6ACNnVIEuRG5RhKE1xQJBAPADdyKqrHOrIRprCB1zdSKJwnu+X+NZzrc6KyKkosI+lKPwMIqW3LjrZI4bXu1gtXYdzFupqmLkPPgRuB+eYn8CQQCVbVRGUwmwO9aug/gvmlUeWhPagkk0mODcZrvZMbN5ni9ma25zwXH83szsKCnFxkqRq+wwtHztEZytTPtrPccvAkAfS0RgV8KOflSdnjD7rg8qxq199u+HtDD4wPPY24CBNSmqSmn51/vFfKOqx7TT3JUsZmw7015s8HdmnODV9o+/AkACv1jeViaGdAM1gD2u+mJ4RWW6Dc00x17TDpyDmNNY+BydAHqubC8hXCNecDqcZVH3rbaRCrCFV16PHSw+Q4uHAkEA0JFY5mVepN9FyMFQoNH+669OvB/3ccljBcFtXw74XmwJXzohIZwZauz7x8wXijW1RHlHxOgsTVQJHrUBW/DSvg==";
		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMGITT+P0zNQTF98PgJFqh/z3Fffc5OIvC2Gzxqoz0vDIeexsafeqGUZCW6w/jgS2SnfmoFHPjaac99fmjI8hH1QwmOuXmeNQhC5jmUaGnLK8YksDqk6iZPVnAQPHd+rDz8OrZU+e3Bm8RKpfdT+N7HoRdQORr0jcuw2PPeefOUQIDAQAB";
		String signature = signature(msg, privateKey);
		System.out.println("私钥签名：" + signature);
		System.out.println("公钥验签：" + verify(msg, signature,publicKey));
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

	/**
	 * 直接以密钥来签名，事先从证书拿到密钥,约定密钥算法为RSA，加签算法为SHA256withRSA
	 * @param message
	 * @param key
	 * @return
	 */
	public static String signature(String message, String key) {
		try {
			Signature signature = Signature.getInstance(Algorithm_SHA256withRSA);
			// 密钥内容的规范
			KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
			// 从规范得到私钥
			PrivateKey privateKey = KeyFactory.getInstance(Algorithm_RSA).generatePrivate(keySpec);
			// 签名
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
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 使用密钥串验签
	 * @param message
	 * @param sign
	 * @param key
	 * @return
	 */
	public static boolean verify(String message, String sign, String key) {
		try {
			Signature signature = Signature.getInstance(Algorithm_SHA256withRSA);
			// 密钥内容的规范
			KeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
			// 从规范得到公钥
			PublicKey publicKey = KeyFactory.getInstance(Algorithm_RSA).generatePublic(keySpec);
			// 初始化验签公钥
			signature.initVerify(publicKey);
			signature.update(message.getBytes(charset_UTF8));
			byte[] decode = Base64.getDecoder().decode(sign);
			return signature.verify(decode);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return false;
	}
    
}
