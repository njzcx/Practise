package com.demo.security;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jetty.util.StringUtil;

import sun.misc.BASE64Encoder;

public class EncryptUtil {
	private EncryptUtil() {}
	
	@SuppressWarnings("restriction")
	public static String encryptBASE64(String source) {
		return new BASE64Encoder().encode(source.getBytes());
	}
	
	/**
	 * 信息-摘要算法;用于文件校验
	 * @param source
	 * @return
	 */
	public static String encryptMD5(String source) {
		//可以查看已注册提供者列表，SUN常用算法MD2 MD5 SHA-1 SHA-256 SHA-384 SHA-512 
		Security.getProviders();
		byte[] md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source.getBytes());
			md5 = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytesToHex(md5);
	}
	
	private static String bytesToHex(byte[] bytes) {
		if (bytes == null)
			return null;
		/*以下是将加密的字节转化为16进制*/
		StringBuffer text = new StringBuffer();
		for (int n = 0; n < bytes.length; n++) {  
            // 字节做"与"运算，去除高位置字节 11111111  
			String tmp = Integer.toHexString(bytes[n] & 0xFF);  
            if (tmp.length() == 1) {  
                // 如果是0至F的单位字符串，则添加0  
            	text.append("0" + tmp);  
            } else {  
            	text.append(tmp);  
            }  
		}
		return text.toString();
	}
	
	/**
	 * 安全散列算法，数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。
	 * 虽然，SHA与MD5通过碰撞法都被破解了，但是SHA仍然是公认的安全加密算法，较之MD5更为安全。
	 * @param source
	 * @return
	 */
	public static String encryptSHA1(String source) {
		byte[] sha1 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(source.getBytes());
			sha1 = md.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return bytesToHex(sha1);
	}
	
	/**
	 * 散列消息鉴别码，基于密钥的Hash算法的认证协议。
	 * 原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
	 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。
	 * 接收方利用与发送方共享的密钥进行鉴别认证等。 
	 * @param source
	 * @return
	 */
	public static String encryptHMAC(String source, String secretkey) {
		byte[] hmac = null;
		byte[] key = null;
		try {
			//此类提供（对称）密钥生成器的功能, 可填入 HmacSHA1，HmacSHA256 等
			if (StringUtil.isBlank(secretkey)) {
				KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");  
				SecretKey secretKey = keyGenerator.generateKey(); 
				key = secretKey.getEncoded();
				System.out.println("GeneratorKey:" + bytesToHex(key));
				
			} else {
				key = secretkey.getBytes();
			}
			//下面为根据密钥，加密数据。密钥可以根据上面生成，也可以使用getBytes生成
			SecretKey secretKey = new SecretKeySpec(key, "HmacMD5");
		    Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
		    mac.init(secretKey);  
		    hmac = mac.doFinal(source.getBytes());
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return bytesToHex(hmac);
	}
	
	/**
	 * 使用系统生成的密钥加密源数据
	 * @param source
	 * @return
	 */
	public static String encryptHMAC(String source) {
		return encryptHMAC(source, null);
	}
	
	/**
	 * DES是一种对称加密算法，加密和解密使用相同密钥的算法。
	 * 近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，24小时内即可被破解。
	 * 加密，则用Key去把数据Data进行加密， 生成Data的密码形式（64位）作为DES的输出结果；
	 * 解密，则用Key去把密码形式的数据Data解密，还原为Data的明码形式（64位）作为DES的输出结果。
	 * DES加密和解密过程中，密钥长度都必须大于8字节
	 * @param source
	 * @param key
	 * @return
	 */
	public static String encryptDES(String source, String key) {
		byte[] des = null;
		 try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
//			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES"); //这样方法也可以，但不能获得标准的DESKey
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());//DES
			// 用密钥初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			des = cipher.doFinal(source.getBytes());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		 return bytesToHex(des);
	}
	
	/**
	 * DES是一种对称加密算法，加密和解密使用相同密钥的算法。
	 * 近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，24小时内即可被破解。
	 * 加密，则用Key去把数据Data进行加密， 生成Data的密码形式（64位）作为DES的输出结果；
	 * 解密，则用Key去把密码形式的数据Data解密，还原为Data的明码形式（64位）作为DES的输出结果。
	 * DES加密和解密过程中，密钥长度都必须大于8字节
	 * @param source
	 * @param key
	 * @return
	 */
	public static String encryptAES(String source, String key) {
		byte[] des = null;
		try {
			// DES算法要求有一个可信任的随机数源
			SecureRandom sr = new SecureRandom();
			// 从原始密钥数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
			SecretKey secretKey = keyFactory.generateSecret(dks);
//			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES"); //这样方法也可以，但不能获得标准的DESKey
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());//DES
			// 用密钥初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
			des = cipher.doFinal(source.getBytes());
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return bytesToHex(des);
	}
	
	/**
	 *  复杂的对称加密（DES、PBE）、非对称加密算法： 

DES(Data Encryption Standard，数据加密算法)
PBE(Password-based encryption，基于密码验证)
RSA(算法的名字以发明者的名字命名：Ron Rivest, AdiShamir 和Leonard Adleman)
DH(Diffie-Hellman算法，密钥一致协议)
DSA(Digital Signature Algorithm，数字签名)
ECC(Elliptic Curves Cryptography，椭圆曲线密码编码学)
	 * @param args
	 */
	
	public static void main(String[] args) {
		String source = "张晨雪测试BASE64";
		String out = EncryptUtil.encryptBASE64(source);
		out = EncryptUtil.encryptHMAC(source, "123a");
		out = EncryptUtil.encryptDES(source, "12345678");
		System.out.println(out);
	}
}
