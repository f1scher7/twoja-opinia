package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	
	public String toSHA256(String text)
	{
		MessageDigest hash;
		try 
		{
			hash = MessageDigest.getInstance("SHA-256");
			byte[] hashbytes = hash.digest(text.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hashbytes);
			
		} catch (NoSuchAlgorithmException e) {
			
			return text;
		}
	}
	
	private static String bytesToHex(byte[] hash) 
	{
	    StringBuilder hexString = new StringBuilder();
	    for (byte b : hash) {
	      hexString.append(String.format("%02x", b));
	    }
	    
	    return hexString.toString();
	}

}
