/**
 Copyright [2020] [Javier Linares Castrillón]
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package domain;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Stream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Javier Linares
 */
public class CipherAES {

    private static String keyUsed;

    private static boolean chars(char c){
        return c >= '!' && c <= 'z';
    }

    /**
     * Generates a random key using Streams.
     * @param length
     * @return the key that is gonna be used to encrypt or decrypt.
     */
    private static String generateKey(long length){

        SecureRandom sr = new SecureRandom(){{generateSeed(12);}};
        Stream<Character> chars = sr.ints(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).mapToObj(i -> (char)i).filter(c -> chars(c)).limit(length);

        String s = chars.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
        return s;
    }


    /**
     * Generates the internal key.
     * @param key used to encrypt
     * @return the key
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec keyFactory(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] encryptationKey = key.getBytes("UTF-8");

        MessageDigest sha = MessageDigest.getInstance("SHA-1");

        encryptationKey = sha.digest(encryptationKey);
        encryptationKey = Arrays.copyOf(encryptationKey, 16);

        SecretKeySpec secretKey = new SecretKeySpec(encryptationKey, "AES");

        return secretKey;
    }


    /**
     *Encrypts a plaintext.
     * @param data
     * @return the ciphertext.
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String encrypt(String data) throws BadPaddingException,  NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, NoSuchPaddingException {
       String randomKey = generateKey(12); //Im using a 12-length key.
       keyUsed = randomKey;
       SecretKeySpec secretKey = keyFactory(randomKey);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
    }

    /**
     * Decrypts the String chain using a key.
     * @param ciphertext
     * @param key
     * @return decrypted plaintext.
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static String decrypt(String ciphertext, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IllegalArgumentException {
        SecretKeySpec secretKey = keyFactory(key);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return  new String(cipher.doFinal(Base64.getDecoder().decode(ciphertext)));
    }

    public static String getKeyUsed() {
        return keyUsed;
    }

    public static void setKeyUsed(String keyUsed) {
        CipherAES.keyUsed = keyUsed;
    }
}
