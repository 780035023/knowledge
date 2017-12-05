package com.ixinnuo.financial.util.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.security.tools.keytool.Main;

/**
 * 密钥和证书管理工具
 * 
 * @author aisino
 *
 */
public class KeyToolUtil {

	public static void main(String[] args) throws Exception {
		//命令：keytool -list -keystore test.keystore
		//这里省略开头的keytool，这里Main本身就是keytool工具,使用空格拆分的命令
		
		//printAllCommand();
		//printCommandHelp("export");
		//生成密钥对,会提示输入密码，机构；域名liquanqiang.aisino.com来倒着填，按地址倒着填
		//execCommand("-genkeypair -alias carl -keyalg RSA -keysize 1024 -validity 365 -keystore carl.keystore");
		//列出密钥库中的条目
		//execCommand("-list -keystore carl.keystore");
		//从密钥库导出公钥到证书
		//execCommand("-export -alias carl -file carl.crt -keystore carl.keystore");
		//打印证书内容
		//execCommand("-printcert -file carl.crt");
		System.out.println(getStrFromKey(getPublicKeyFromCert(getCertFromKeyStore("JKS", "carl.keystore", "qq2476056494", "carl"))));
		System.out.println(getStrFromKey(getPublicKeyFromCert(getCert("carl.crt"))));
		System.out.println(getStrFromKey(getPrivateKeyFromKeyStore("JKS", "carl.keystore", "qq2476056494", "carl","qq2476056494")));
	}
	
	/**
	 * 打印keytool的所有命令
	 */
	public static void printAllCommand(){
		try {
			Main.main(("-help").split("\\s"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打印keytool的指定命令帮助信息
	 * @param commandName
	 */
	public static void printCommandHelp(String commandName){
		try {
			Main.main(("-"+commandName + " -help").split("\\s"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用的命令执行
	 * @param command
	 */
	public static void execCommand(String command){
		try {
			Main.main((command).split("\\s"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取密钥库中的证书
	 * @param keyStoreType 密钥库的条目类型，-list可以查看
	 * @param keyStorePath 密钥库的文件路径
	 * @param keyStorePass 密钥库密码
	 * @param alias 条目别名
	 * @return
	 */
	public static Certificate getCertFromKeyStore(String keyStoreType,String keyStorePath,String keyStorePass,String alias){
		try {
			//因为生成证书的类型为JKS 也有其他的格式  -list可以查看密钥库的条目类型
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);  
			//读取keystore文件转换为keystore密钥库对象  
			FileInputStream fis = new FileInputStream(keyStorePath);  
			//该密钥库的密码
			keyStore.load(fis, keyStorePass.toCharArray());  
			fis.close();  
			// 从keystore中获取证书然后进一步获取公钥
			Certificate certificate = keyStore.getCertificate(alias);
			return certificate;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	
	/**
	 * 直接读取证书
	 * @param certPath 证书目录
	 * @return
	 */
	public static Certificate getCert(String certPath){
		try {
			//证书类型只支持X.509
			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			// 取得证书文件流
			FileInputStream inputStream = new FileInputStream(certPath);
			// 生成证书
			Certificate certificate = factory.generateCertificate(inputStream);
			inputStream.close();
			return certificate;
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 从证书读取公钥,java只支持X509Certificate
	 * @param certificate
	 * @return
	 */
	public static PublicKey getPublicKeyFromCert(Certificate certificate){
		 //读取公钥对象  
		X509Certificate x509Certificate = (X509Certificate) certificate;
		PublicKey publicKey = certificate.getPublicKey();
		System.out.println("证书算法：" + x509Certificate.getSigAlgName());
		System.out.println("公钥算法：" + publicKey.getAlgorithm());
		//64位编码处理
		return publicKey;
	}
	
 
	/**
	 * 获取密钥库中的私钥内容
	 * @param keyStoreType 密钥库的条目类型，-list可以查看
	 * @param keyStorePath 密钥库的文件路径
	 * @param keyStorePass 密钥库密码
	 * @param alias 条目别名
	 * @param aliasPass 条目密码
	 * @return
	 */
	public static PrivateKey getPrivateKeyFromKeyStore(String keyStoreType,String keyStorePath,String keyStorePass,String alias,String aliasPass){
		try {
			//因为生成证书的类型为JKS 也有其他的格式  -list可以查看密钥库的条目类型
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);  
			//读取keystore文件转换为keystore密钥库对象  
			FileInputStream fis = new FileInputStream(keyStorePath);  
			//该密钥库的密码
			keyStore.load(fis, keyStorePass.toCharArray());  
			fis.close();  
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, aliasPass.toCharArray());
			System.out.println("私钥算法："+privateKey.getAlgorithm());
			//64位编码处理
			return privateKey;
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public static String getStrFromKey(Key privateKey){
		//64位编码处理
		return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}
	
	
	/**
	 * 从字符串还原回key
	 * @param encodedKey
	 * @param algorithm
	 * @return
	 */
	public static SecretKey getKeyFromStr(String encodedKey,String algorithm){
		// decode the base64 encoded string
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		// rebuild key using SecretKeySpec
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm); 
		return originalKey;
	}
}
