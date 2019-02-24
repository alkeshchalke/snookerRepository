/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * ===========================================================================
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates.
 * All rights reserved.
 * ===========================================================================
 *
 *  $Log:
 *   7    360Commerce 1.6         12/24/2007 3:54:45 PM  Anil Bondalapati
 *        changed the access scope for methods.
 *   6    360Commerce 1.5         12/24/2007 3:32:24 PM  Anil Bondalapati added
 *         validation method.
 *   5    360Commerce 1.4         12/19/2007 3:53:19 PM  Anil Bondalapati
 *        updated.
 *   4    360Commerce 1.3         12/14/2007 9:35:21 AM  Anil Bondalapati
 *        updated.
 *   3    360Commerce 1.2         12/13/2007 6:09:10 PM  Anil Bondalapati
 *        updated
 *   2    360Commerce 1.1         12/13/2007 6:07:59 PM  Anil Bondalapati This
 *        class is used to encrypt and decrypt the database properties.
 *   1    360Commerce 1.0         12/13/2007 6:04:57 PM  Anil Bondalapati This
 *        class is used to encrypt and decrypt the properties.
 *  $
 * ============================================================================
*/
package com.projectwork.impl;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import org.apache.axis.encoding.Base64;
/**
 * This class is used to encrypt and decrypt the database properties
 *
 * The value of the property which starts with "!",
 * will be encrypted for the first time and from the next time
 * onwards just decrypt and return the value to the caller.
 *
 * @author abond
 *
 */

public class StringEncryptionUtility implements StringEncryptionUtilityIfc
{

	/**
	 * Get encrypted database password from the clear text.
	 * @param encryptedByteText
	 * @return
	 */
	public  String getAppLevelEncodedString(String plainString)
	{
	    byte[] clearText = plainString.getBytes();
		byte[] encBytes = new  byte[clearText.length];

		for (int i = 0; i < clearText.length; i++) {

			byte add = (byte)(clearText[i] + 12);

			encBytes[i]  = (byte)(add ^ 24);
		}

		return Base64.encode(encBytes);

	}

    /**
     * Get encoded password from the clear text.
     * @param encryptedByteText
     * @return
     */
    public String getBase64encode(byte[] clearText)
    {
        return Base64.encode(clearText);
    }

	/**
	 * Get decrypted database password from the encrypted data.
	 * @param encryptedText
	 * @return
	 */
	public String  getAppLevelDecodedString(String encryptedText)
	{

		byte[] base64Bytes =   Base64.decode(encryptedText);

		byte[] decBytes = new byte[base64Bytes.length];

		for (int i = 0; i < base64Bytes.length; i++) {

			 byte xorByte = (byte)(base64Bytes[i] ^ 24);

			 decBytes[i]= (byte)(xorByte - 12);
		}

		return new String(decBytes);

	}
	
	
	/**
	 * 
	 * @param plainString
	 * @return String
	 */
	public  String getDBLevelEncodedString(String plainString)
	    {
	        byte[] clearText = plainString.getBytes();
	        byte[] encBytes = new  byte[clearText.length];

	        for (int i = 0; i < clearText.length; i++) {

	            byte add = (byte)(clearText[i] + 12);

	            encBytes[i]  = (byte)(add ^ 24);
	        }

	        return Base64.encode(encBytes);

	    }

	
	
	   /**
     * Get decrypted database password from the encrypted data.
     * @param encryptedText
     * @return
     */
    public String  getDBLevelDecodedString(String encryptedText)
    {

        byte[] base64Bytes =   Base64.decode(encryptedText);

        byte[] decBytes = new byte[base64Bytes.length];

        for (int i = 0; i < base64Bytes.length; i++) {

             byte xorByte = (byte)(base64Bytes[i] ^ 24);

             decBytes[i]= (byte)(xorByte - 12);
        }

        return new String(decBytes);

    }

    /**
     * Get decoded password from the encrypted data.
     * @param encryptedText
     * @return
     */
    public String getBase64decode(String encryptedText)
    {
        byte[] base64Bytes =   Base64.decode(encryptedText);
        return new String(base64Bytes);
    }

	/**
	 * validate the password
	 * @param password
	 * @return
	 */
	public String validatePassword(String password)
	{
		if(password != null && password.length() >= 7 )
		{
			if(password.matches(".*[0-9]{1}.*") && password.matches(".*[a-zA-Z]{1}.*"))
			{
				return "SUCCESS";
			}

		}

		return "FAILED";
	}

	/**
     * Returns hashed password
     * @param plainText
     * @param algorithm
     * @return
     */
    protected String getHashedPassword (String plainText, String algorithm)
    {
        byte[] hash = null;
        try
        {
            hash = plainText.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            hash = plainText.getBytes();
        }

        try
        {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            hash = digest.digest(hash);
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.print(algorithm + "digest algorithm not available");
        }


        return getBase64encode(hash);
    }

    static class OperationsEnum
    {
        protected String operation;
        protected int code;

        /** The map containing the singleton operation instances */
        protected static HashMap<String, OperationsEnum> operations = new HashMap<String, OperationsEnum>();

        private OperationsEnum(String operation, int code)
        {
            this.operation = operation;
            this.code = code;
            operations.put(operation, this);
        }

        public String toString() {
            return getOperation();
        }

        public String getOperation() {
            return operation;
        }

        public int getCode() {
            return code;
        }

        public static int getOperationCode(String operation)
        {
            return ((OperationsEnum)operations.get(operation)).getCode();
        }

        /** encrypt the password **/
        protected static final int ENCRYPT = 1;
        public static final OperationsEnum ENCRYPT_ENUM = new OperationsEnum("encrypt", ENCRYPT);
        /** decrypt the password **/
        protected static final int DECRYPT = 2;
        public static final OperationsEnum DECRYPT_ENUM = new OperationsEnum("decrypt", DECRYPT);
        /** validate the password **/
        protected static final int VALIDATE = 3;
        public static final OperationsEnum VALIDATE_ENUM = new OperationsEnum("validate", VALIDATE);
        /** encode the password **/
        protected static final int ENCODE = 4;
        public static final OperationsEnum ENCODE_ENUM = new OperationsEnum("encode", ENCODE);
        /** decode the password **/
        protected static final int DECODE = 5;
        public static final OperationsEnum DECODE_ENUM = new OperationsEnum("decode", DECODE);
        /** hash the password **/
        protected static final int HASH = 6;
        public static final OperationsEnum HASH_ENUM = new OperationsEnum("hash", HASH);

    }

    public static void main(String[] agrs)
    {
        StringEncryptionUtility util= new StringEncryptionUtility();
        System.out.print(">> Encrypted " + util.getAppLevelEncodedString("6000000000000"));
        System.out.print(">> Encrypted " + util.getAppLevelEncodedString("12345"));
        System.out.print(">> Decrypted " + util.getAppLevelDecodedString("WVsnWFlaW1xdJCU="));
        
    }
}




