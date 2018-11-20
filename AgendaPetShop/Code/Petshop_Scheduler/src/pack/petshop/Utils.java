package pack.petshop;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.ResultSet;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Utils 
{
	private static byte[] Encrypt(String data)
	{
		return Hash(data);
	}
	
	private static byte[] Hash(String data)
	{
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		KeySpec spec = new PBEKeySpec(data.toCharArray(), salt, 65536, 128);
		byte[] hash;
		try 
		{
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
			return hash;
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} 
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet ReadEspecie()
	{
		
	}
}
