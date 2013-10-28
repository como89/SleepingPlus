package net.como89.sleepingplus.data;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Encrypter
{

	SecretKeySpec secretKey = null;
	Cipher aesCipher = null;

	/**
	*
	* Encrypter 
	*	
	*	@author Nba_Yoh
	*	@version 1.0
	*	@param stringKey : The secret key used to encrypt.
	*/
	Encrypter(String stringKey)
	{
		byte[] key = stringKey.getBytes();
		SecretKeySpec secretKeySpec = null;
		Cipher desCipher = null;

		try
		{
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			
			secretKeySpec = new SecretKeySpec(key, "AES");

			desCipher = Cipher.getInstance("AES");
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchPaddingException e)
		{
			e.printStackTrace();
		}

		this.secretKey = secretKeySpec;
		this.aesCipher = desCipher;
	}

	/**
	*
	* Used to encrypt a string. 
	*	
	*	@author Nba_Yoh
	*	@version 1.0
	*	@param s : The string that you want to encrypt.
	*	@return Return the encrypted string.
	*/
	String encrypt(String s)
	{

		String encryptedString = null;

		try
		{
			aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			encryptedString = Base64Coder.encodeLines(aesCipher.doFinal(s.getBytes()));

		}
		catch(InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch(IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch(BadPaddingException e)
		{
			e.printStackTrace();
		} 

		return encryptedString;
	}
	
	/**
	*
	* Used to encrypt a string. 
	*	
	*	@author Nba_Yoh
	*	@version 1.0
	*	@param s : The string that you want to decrypt.
	*	@return Return the decrypted string.
	*/
	String decrypt(String s)
	{
		String encryptedString = null;

		try
		{
			aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decode = Base64Coder.decodeLines(s);
			encryptedString = new String(aesCipher.doFinal(decode), "UTF8");

		}
		catch(InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch(IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch(BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		return encryptedString;
	}

}
