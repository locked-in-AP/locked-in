package com.locked_in.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility class for password encryption and decryption operations.
 * 
 * This utility class provides secure password handling using:
 * - AES/GCM encryption with NoPadding
 * - PBKDF2 key derivation with HMAC-SHA256
 * - Secure random number generation for IVs and salts
 * - Base64 encoding for encrypted data storage
 * 
 * The class implements industry-standard security practices including:
 * - 256-bit AES keys
 * - 128-bit authentication tags
 * - 12-byte initialization vectors
 * - 16-byte salt values
 * - 65,536 PBKDF2 iterations
 */
public class PasswordUtil {
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

   
    /**
     * Generates a cryptographically secure random nonce.
     * 
     * Uses SecureRandom to generate a random byte array
     * suitable for use as an initialization vector or salt.
     * 
     * @param numBytes the length of the nonce in bytes
     * @return a random byte array of the specified length
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates a new AES secret key.
     * 
     * Creates a cryptographically secure AES key using
     * the specified key size and a strong random number generator.
     * 
     * @param keysize the size of the key in bits (must be valid AES key size)
     * @return a new AES SecretKey
     * @throws NoSuchAlgorithmException if the AES algorithm is not available
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives an AES key from a password and salt.
     * 
     * Uses PBKDF2 with HMAC-SHA256 to derive a secure key from the password.
     * Implements key stretching with 65,536 iterations for security.
     * 
     * @param password the password to derive the key from
     * @param salt the salt to use in key derivation
     * @return a SecretKey derived from the password and salt
     */
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt){
           	try {
           		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
           		// iterationCount = 65536
           		// keyLength = 256
           		KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
           		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
           		return secret;
       		} catch (NoSuchAlgorithmException ex) {
       			Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
           	} catch (InvalidKeySpecException ex) {
           		Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
           	}
       		return null;
    }

    /**
     * Encrypts a password using AES-GCM.
     * 
     * This method:
     * 1. Generates a random salt and IV
     * 2. Derives an AES key from the employee ID and salt
     * 3. Encrypts the password using AES-GCM
     * 4. Combines IV, salt, and ciphertext
     * 5. Returns the result as a Base64-encoded string
     * 
     * @param employee_id used as the key derivation password
     * @param password the password to encrypt
     * @return Base64-encoded encrypted password, or null if encryption fails
     */
    public static String encrypt(String employee_id, String password){
    	try {
		    // 16 bytes salt
		    byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);
		
		    // GCM recommended 12 bytes iv?
		    byte[] iv = getRandomNonce(IV_LENGTH_BYTE);
		
		    // secret key from password
		    SecretKey aesKeyFromPassword = getAESKeyFromPassword(employee_id.toCharArray(), salt);
		
		    Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
		
		    // ASE-GCM needs GCMParameterSpec
		    cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
		
		    byte[] cipherText = cipher.doFinal(password.getBytes());
		
		    // prefix IV and Salt to cipher text
		    byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
		            .put(iv)
		            .put(salt)
		            .put(cipherText)
		            .array();
		
		    // string representation, base64, send this string to other for decryption.
		    return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
    	}catch(Exception ex) {
    		return null;
    	}

    }

    
    /**
     * Decrypts an encrypted password.
     * 
     * This method:
     * 1. Decodes the Base64-encoded input
     * 2. Extracts the IV and salt
     * 3. Derives the AES key from the username and salt
     * 4. Decrypts the ciphertext using AES-GCM
     * 
     * @param encryptedPassword the Base64-encoded encrypted password
     * @param username used as the key derivation password
     * @return the decrypted password, or null if decryption fails
     */
    public static String decrypt(String encryptedPassword, String username) {
		try {
			byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));
	
			// get back the iv and salt from the cipher text
			ByteBuffer bb = ByteBuffer.wrap(decode);
	
			byte[] iv = new byte[IV_LENGTH_BYTE];
			bb.get(iv);
	
			byte[] salt = new byte[SALT_LENGTH_BYTE];
			bb.get(salt);
	
			byte[] cipherText = new byte[bb.remaining()];
			bb.get(cipherText);
	
			// get back the aes key from the same password and salt
			SecretKey aesKeyFromPassword = PasswordUtil.getAESKeyFromPassword(username.toCharArray(), salt);
	
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
	
			cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
	
			byte[] plainText = cipher.doFinal(cipherText);
		
			return new String(plainText, UTF_8);
		}catch(Exception ex) {
			return null;
		}

	}
 
}
