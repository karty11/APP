package com.keystoneconstructs.credentia.Utils;

import com.keystoneconstructs.credentia.constant.Constants;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public interface EncryptionUtils {

    /**
     * This method encrypts the password using PBKDF2 hash algorithm.
     * @param password
     * @param salt
     * @return encodedString
     * @throws Exception
     */
    static String getEncryptedPassword( String password,
            String salt ) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String algorithm = Constants.ENCRYPT_ALGO;
        int derivedKeyLength = 256;
        int iterations = 20000;

        byte[] saltBytes = Base64.getDecoder().decode( salt );
        KeySpec spec = new PBEKeySpec( password.toCharArray(), saltBytes, iterations, derivedKeyLength );
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( algorithm );

        byte[] encodedBytes = keyFactory.generateSecret( spec ).getEncoded();
        return Base64.getEncoder().encodeToString( encodedBytes );

    }

    /**
     * This method generates a new Salt as String.
     * @return salt
     * @throws NoSuchAlgorithmException
     */
    static String getNewSalt() throws NoSuchAlgorithmException {

        SecureRandom random = SecureRandom.getInstance( "SHA1PRNG" );
        byte[] salt = new byte[ 8 ];

        random.nextBytes( salt );

        return Base64.getEncoder().encodeToString( salt );

    }


}
