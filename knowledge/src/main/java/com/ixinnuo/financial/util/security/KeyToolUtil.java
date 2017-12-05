package com.ixinnuo.financial.util.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Base64;

import sun.misc.BASE64Encoder;
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
		//printCommandHelp("list");
		//生成密钥对,会提示输入密码，机构；域名liquanqiang.aisino.com来倒着填，按地址倒着填
		//execCommand("-genkeypair -alias carl -keyalg RSA -keysize 1024 -validity 365 -keystore carl.keystore");
		//列出密钥库中的条目
		//execCommand("-list -keystore carl.keystore");
		//从密钥库导出公钥到证书
		//execCommand("-export -alias carl -file carl.crt -keystore carl.keystore");
		//打印证书内容
		//execCommand("-printcert -file carl.crt");
		//System.out.println(getPublicKey("JKS", "carl.keystore", "qq2476056494", "carl"));
		System.out.println(getPrivateKey("JKS", "carl.keystore", "qq2476056494", "carl","qq2476056494"));
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
	 * 获取密钥库中的公钥内容
	 * @param keyStoreType 密钥库的条目类型，-list可以查看
	 * @param keyStorePath 密钥库的文件路径
	 * @param keyStorePass 密钥库密码
	 * @param alias 条目别名
	 * @return
	 */
	public static String getPublicKey(String keyStoreType,String keyStorePath,String keyStorePass,String alias){
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
			 //读取公钥对象  
			PublicKey publicKey = certificate.getPublicKey();
			//64位编码处理
			return Base64.getEncoder().encodeToString(publicKey.getEncoded());
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
	 * 获取密钥库中的私钥内容
	 * @param keyStoreType 密钥库的条目类型，-list可以查看
	 * @param keyStorePath 密钥库的文件路径
	 * @param keyStorePass 密钥库密码
	 * @param alias 条目别名
	 * @param aliasPass 条目密码
	 * @return
	 */
	public static String getPrivateKey(String keyStoreType,String keyStorePath,String keyStorePass,String alias,String aliasPass){
		try {
			//因为生成证书的类型为JKS 也有其他的格式  -list可以查看密钥库的条目类型
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);  
			//读取keystore文件转换为keystore密钥库对象  
			FileInputStream fis = new FileInputStream(keyStorePath);  
			//该密钥库的密码
			keyStore.load(fis, keyStorePass.toCharArray());  
			fis.close();  
			Key privateKey = keyStore.getKey(alias, aliasPass.toCharArray());
			//64位编码处理
			return Base64.getEncoder().encodeToString(privateKey.getEncoded());
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
	

 
}
