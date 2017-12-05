package com.ixinnuo.financial.util.security;

public class BytesHexStrTranslate {

	/*Convert
	byte[] to
	hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。*
	@param src byte[] data*@return
	hex string*/

	public static String bytesToHexString(byte[] src){
       StringBuilder stringBuilder = new StringBuilder("");
       if (src == null || src.length <= 0) {
           return null;
       }
       
       for (int i = 0; i < src.length; i++) {
           int v = src[i] & 0xFF;
           String hv = Integer.toHexString(v);
           if (hv.length() < 2) {
               stringBuilder.append(0);
           }
           stringBuilder.append(hv);
       }
       return stringBuilder.toString();
   }

	/**
    * Convert hex string to byte[]
    * 16进制转换成字节数组
    * @param hexString the hex string
    * @return byte[]
    */
   public static byte[] hexStringToBytes(String hexString) {
       if (hexString == null || hexString.equals("")) {
           return null;
       }
       hexString = hexString.toUpperCase();
       char[] hexChars = hexString.toCharArray();
       int length = hexString.length() / 2;
       byte[] d = new byte[length];//一个byte表示2个16进制数
       for (int i = 0; i < length; i++) {
           int pos = i * 2;
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
       }
       return d;
   }

	/**
    * Convert char to byte  
    * 一个byte为8位，可用两个十六进制位标识,一个char位2个字节，16位
    * @param c char
    * @return byte
    */
    private static byte charToByte(char c) {
       return (byte) "0123456789ABCDEF".indexOf(c);
   }
    
    
	public static void main(String[] args) throws Exception {
		//listAlgorithm();
		// summaryMD5("你好");
		System.out.println(bytesToHexString("你好".getBytes("utf-8")));
		System.out.println(new String(hexStringToBytes("e4bda0e5a5bd"),"utf-8"));
	}
}
