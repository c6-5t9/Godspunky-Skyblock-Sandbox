package net.hypixel.skyblock.util;

import java.util.Base64;
import java.security.Key;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import java.security.spec.KeySpec;

public class SkyEncryption
{
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private final KeySpec ks;
    private final SecretKeyFactory skf;
    private final Cipher cipher;
    byte[] arrayBytes;
    private final String myEncryptionKey;
    private final String myEncryptionScheme;
    SecretKey key;
    
    public SkyEncryption() throws Exception {
        this.myEncryptionKey = "ThisIsSpartaThisIsSparta";
        this.myEncryptionScheme = "DESede";
        this.arrayBytes = this.myEncryptionKey.getBytes(StandardCharsets.UTF_8);
        this.ks = (KeySpec)new DESedeKeySpec(this.arrayBytes);
        this.skf = SecretKeyFactory.getInstance(this.myEncryptionScheme);
        this.cipher = Cipher.getInstance(this.myEncryptionScheme);
        this.key = this.skf.generateSecret(this.ks);
    }
    
    public String encrypt(final String unencryptedString) {
        String encryptedString = null;
        try {
            this.cipher.init(1, (Key)this.key);
            final byte[] plainText = unencryptedString.getBytes(StandardCharsets.UTF_8);
            final byte[] encryptedText = this.cipher.doFinal(plainText);
            encryptedString = new String(Base64.getEncoder().encode(encryptedText));
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }
    
    public String decrypt(final String encryptedString) {
        String decryptedText = null;
        try {
            this.cipher.init(2, (Key)this.key);
            final byte[] encryptedText = Base64.getDecoder().decode(encryptedString);
            final byte[] plainText = this.cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}
