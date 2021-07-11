package com.rajaryan.todo_list.utils;

import android.util.Base64;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class CypherUtils {
    private static final String PASSWORD="I AM A D3VIL OF MY WORLD";
    private static final String ALGORITHM="DES";

    public static String encryptIt(@NonNull String value){
        try {
            DESKeySpec keySpec=new DESKeySpec(PASSWORD.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory factory=SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey key =factory.generateSecret(keySpec);
            byte[] clearText =value.getBytes(StandardCharsets.UTF_8);
            Cipher cypher=Cipher.getInstance(ALGORITHM);
            cypher.init(Cipher.ENCRYPT_MODE,key);
            return android.util.Base64.encodeToString(cypher.doFinal(clearText), Base64.DEFAULT);
        }catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException| IllegalBlockSizeException e){
            e.printStackTrace();
        }
        return value;
    }
    public String decryptIt(@NonNull String value){
        try {
            DESKeySpec keySpec=new DESKeySpec(PASSWORD.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory factory=SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey key =factory.generateSecret(keySpec);
            byte[] encryptByte =Base64.decode(value,Base64.DEFAULT);
            Cipher cypher=Cipher.getInstance(ALGORITHM);
            cypher.init(Cipher.DECRYPT_MODE ,key);
            byte[] dycrypt=(cypher.doFinal(encryptByte));
            return new String(dycrypt);
        }catch (InvalidKeyException | InvalidKeySpecException | NoSuchAlgorithmException | BadPaddingException | NoSuchPaddingException| IllegalBlockSizeException e){
            e.printStackTrace();
        }
        return value;
    }
}
