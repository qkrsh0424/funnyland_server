package com.funnyland.funnyland_server.service.handler;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.funnyland.funnyland_server.crypto_handler.RSAUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RSAHandlerService {
    @Value("${app.ciper.publickey}")
    private String publicKey;
    @Value("${app.ciper.privatekey}")
    private String privateKey;
    
    public String getEncryptRsaStr(String text) {
        try {
            PublicKey rePublicKey = RSAUtil.getPublicKeyFromBase64Encrypted(publicKey);
            String encryptedRe = RSAUtil.encryptRSA(text, rePublicKey);
            return encryptedRe;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    public String getDecryptRsaStr(String text) {
        try {
            PrivateKey rePrivateKey = RSAUtil.getPrivateKeyFromBase64Encrypted(privateKey);
            String decryptedRe = "";
            if(!text.equals("") && text!=null){
                decryptedRe = RSAUtil.decryptRSA(text, rePrivateKey);   
            }
            return decryptedRe;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }
}
