package xahla.utils.system;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import xahla.utils.ValidationException;

/**
 * This class contains several String, Char and Alphanumeric utils functions.
 * 
 * @author Cochetooo
 * @version 1.1
 */
public class StringUtils {

	/** The list of hexadecimal numbers. */
	final public static char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
	
	/**
	 * @param text 	The text.
	 * @return 		The text encrypted in MD5.
	 * @see MessageDigest
	 */
	public static String toMD5(String text) {
		try {
			String result = text;
			if (text != null) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(text.getBytes());
				BigInteger hash = new BigInteger(1, md.digest());
				result = hash.toString(16);
				while(result.length() < 32) {
					result = "0" + result;
				}
			}
			
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Converts a byte to hexadecimal String.
	 * @param b 	The byte to convert.
	 * @return 		The hexadecimal String.
	 * @see {@link Integer#toHexString(int)}
	 */
	public static String byteToHex(byte b) {
		return Integer.toHexString(Byte.toUnsignedInt(b));
	}
	
	/**
	 * Puts the first letter in uppercase.
	 * @param str 	The text.
	 * @return 		The text with the first letter in uppercase.
	 * @see {@link String#substring(int, int)}
	 * @see {@link String#toUpperCase()}
	 */
	public static String ucfirst(String str) {
		if (str == null || str.isEmpty()) return str;
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * Interprets a given string as a boolean value.
	 * @param str	The char sequence to be interpreted.
	 * @return True if the value is "true", "on", "enable", "enabled" or "1",<br>
	 * False if the value is "false", "off", "disable", "disabled" or "0",<br>
	 * Throws a ValidationException else.
	 * @throws ValidationException
	 */
	public static boolean parseBoolean(String str) {
		switch(str.toLowerCase()) {
		case "true":
		case "on":
		case "enable":
		case "enabled":
		case "1":
			return true;
		case "false":
		case "off":
		case "disable":
		case "disabled":
		case "0":
			return false;
		default:
			throw new ValidationException();
		}
	}
}
