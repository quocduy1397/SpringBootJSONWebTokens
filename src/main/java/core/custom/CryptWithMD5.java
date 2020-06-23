package core.custom;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CryptWithMD5 {
	
	private static MessageDigest messageDigest;
	
	public static String cryptWithMD5(String password){
	    try 
	    {
	    	messageDigest = MessageDigest.getInstance("MD5");
	        byte[] passBytes = password.getBytes();
	        messageDigest.reset();
	        byte[] digested = messageDigest.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0; i < digested.length; i++)
	        {
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } 
	    catch (NoSuchAlgorithmException ex) 
	    {
	        Logger.getLogger(CryptWithMD5.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    
	    return null;
	}
}
