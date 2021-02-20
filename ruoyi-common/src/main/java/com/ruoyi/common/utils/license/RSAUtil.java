package com.ruoyi.common.utils.license;

/**
 * 
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.naming.ConfigurationException;

import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PEMWriter;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。
 * 
 */
public class RSAUtil
{

	public static String CIPHER = "RSA/ECB/NoPadding";

	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair() throws Exception
	{
		try
		{
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			saveKeyPair(keyPair);
			return keyPair;
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	public static KeyPair getKeyPair() throws Exception
	{
		FileInputStream fis = new FileInputStream("C:/RSAKey.txt");
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	public static void saveKeyPair(KeyPair kp) throws Exception
	{

		FileOutputStream fos = new FileOutputStream("C:/RSAKey.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception
	{
		KeyFactory keyFac = null;
		try
		{
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new Exception(ex.getMessage());
		}

		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try
		{
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		}
		catch (InvalidKeySpecException ex)
		{
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception
	{
		KeyFactory keyFac = null;
		try
		{
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new Exception(ex.getMessage());
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try
		{
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		}
		catch (InvalidKeySpecException ex)
		{
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception
	{
	    Cipher cipher = Cipher.getInstance(CIPHER, new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pk);
		return encrypt(cipher, data);
	}

	public static byte[] encrypt(Cipher cipher, byte[] data) throws Exception
    {
        try
		{
			int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0)
			{
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
				// OutputSize所以只好用dofinal方法。

				i++;
			}
			return raw;
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
    }

	public static byte[] decrypt(Cipher cipher, byte[] raw) throws Exception
	{
		try
		{
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(blockSize);
			int j = 0;

			while (raw.length - j * blockSize > 0)
			{
				if (raw.length - j * blockSize >= blockSize)
				{
					bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				}
				else
				{
					bout.write(cipher.doFinal(raw, j * blockSize, raw.length - j * blockSize));
				}
				j++;
			}

			raw = bout.toByteArray();

			// 和客户端通信特殊处理
			boolean b = false;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			for (int i = raw.length - 1; i >= 0; i--)
			{
				if (raw[i] != 0)
				{
					baos.write(raw, 0, i + 1);
					b = true;
					break;
				}
			}
			if (b)
			{
				return baos.toByteArray();
			}
			else
			{
				return raw;
			}

		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception
	{
		try
		{
			Cipher cipher = getCipher(pk);
			return decrypt(cipher, raw);

		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	/**
     * * 加密 *
     * 
     * @param key 加密的密钥 *
     * @param data 待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(PrivateKey pk, byte[] data) throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER, new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.ENCRYPT_MODE, pk);
        return encrypt(cipher, data);
    }

    public static byte[] decryptByPublicKey(PublicKey pk, byte[] raw) throws Exception
    {
        Cipher cipher = Cipher.getInstance(CIPHER, new org.bouncycastle.jce.provider.BouncyCastleProvider());
        cipher.init(Cipher.DECRYPT_MODE, pk);
        return decrypt(cipher, raw);
    }
    
	public static Cipher getCipher(PrivateKey pk) throws NoSuchAlgorithmException, NoSuchPaddingException,
		InvalidKeyException
	{
		Cipher cipher = Cipher.getInstance(RSAUtil.CIPHER, new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, pk);
		return cipher;
	}

	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent)
	{
		try
		{
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 * 
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent)
	{
		try
		{
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static void test() throws Exception {
		
        KeyPair keyPair =  generateKeyPair();
		
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        String modulStr = publicKey.getModulus().toString();
        System.out.println("modulStr:"+modulStr);
        
        String publicStr = publicKey.getPublicExponent().toString();
        System.out.println("publicStr:"+publicStr);
        
        String privateStr = privateKey.getPrivateExponent().toString();
        System.out.println("privateStr:"+privateStr);
	}

	public static void writeFile(byte[] b)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(new File("c:/fab.txt"));
			fos.write(b);
			fos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static PrivateKey getprivateKey()
	{
		PrivateKey privateKey = null;
		try
		{
			String privateKeyValue = new String(
					"MIICXQIBAAKBgQC1zu1w/jAhIAvZ1J5wtzs6Ouc2VaXLHnIBo8MonAd6HKKB2qesbuv/xqTdb+nk9exdAwNN32H4fypsaF/enKgwJdSjg4a13/h/Jstgf2bQnXqQ6VDHii52wWLSuk8ytx3ysMgBQZElaBg6FUUHKc7dleDKOmvr6vpJ0YsM7ggGXwIDAQABAoGBAKsp+Zkbdix5gy69Yb00hGo58lvRHpmwPB0XFDEzVEa+lrAyLxHEDCNt3OEs78NDVBftMjsqR44wCrytNzTZv+ybsUVxTbRV9nZ7n0EuRGqqQ4T/6+ur8SCKClbnB3X/e96atHxY9fHFbkzGWWvp0paIz38lZTRjIk1g8KL+n5EBAkEA8TDt9hclcltkLU4a3U/F8RmVwm2lFck6RXdCJ9nmBtmRglTb7m2NeKSRUq7hqIzce198TfZOres+IzYQwvgx4QJBAMD4nKFr47og1FTcsrtX2M55tnppzLwSd8Udd19/Uc37y3RO1jQcXNrJ99bkNO0XURnQ82trsuD8uJX1xYTrwD8CQAnRoB5gmtmo9s8RWu6fjMNf80TTCeF1K7YgQMU2jbLBsLbJlZqDQzsiF/hxviYKdf9fx8O0v0c9SG5OiSgmyoECQBEAVlxqcv/dEJgBccXLsZBPbOJQG/ed8Otu8yt4vi5VttEieQDSVuP301wdcwRIf560qB2YuYPVADZGFwbk9mUCQQCkVmcSxXSlsKyw7AnV/z4i72Dgsfabi6uV/IUmIz4aEyOXCag0VK10fjsG4L66PM79LuPNtJZvy3Twy2bzHbl5");
			KeyFactory keyFactory;

			keyFactory = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(toBytes(privateKeyValue));
			privateKey = keyFactory.generatePrivate(priPKCS8);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return privateKey;
	}

	public static final byte[] toBytes(String s)
	{
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++)
		{
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	public static void saveKeyPair(RSAPublicKey kp, String path) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(path);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	public static void saveKey(Key key, String keypath) throws ConfigurationException
	{
		try
		{
			PEMWriter pw1 = new PEMWriter(new OutputStreamWriter(new FileOutputStream(keypath)));
			pw1.writeObject(key);
			pw1.flush();
			pw1.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static PrivateKey readPrivateKey(String keypath)
	{
		try
		{
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			PEMReader ped = new PEMReader(new InputStreamReader(new FileInputStream(new File(keypath))));
			Object readObject = ped.readObject();
			KeyPair keyPair = (KeyPair) readObject;
			return keyPair.getPrivate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * * 解密js *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	public static byte[] decryptJs(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	
	/**
     * 16进制 To byte[]
     * @param hexString
     * @return byte[]
     */
    public  static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
     private  static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
	
	public static String toHexString(byte[] b)
	{
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++)
		{
			sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEXCHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	private static char[] HEXCHAR =
	{
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	};
}
