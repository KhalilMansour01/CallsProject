package com.example.backend;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;

public class JwtSecretMakerTest {
    
    @Test
    public void generateSecretKey() {
        SecretKey secret = Jwts.SIG.HS256.key().build();
        String encodeString = DatatypeConverter.printHexBinary(secret.getEncoded());
        System.out.println("Key = " + encodeString);
    }
}
