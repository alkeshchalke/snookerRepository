package com.projectwork.impl;

public interface StringEncryptionUtilityIfc
{

    public  String getAppLevelEncodedString(String plainString);
    public String  getAppLevelDecodedString(String encryptedText);
    public  String getDBLevelEncodedString(String plainString);
    public String  getDBLevelDecodedString(String encryptedText);
    
}
