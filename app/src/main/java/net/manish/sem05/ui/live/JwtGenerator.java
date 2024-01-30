package net.manish.sem05.ui.live;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JwtGenerator {

    public static String createToken(String apiKey, String apiSecret, String name) {
    try {
        long currentTimeInSeconds = System.currentTimeMillis()/1000 ;

        // Calculate current time in milliseconds
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 45); // Add 30 minutes to the current time
        long time30MinAheadInSeconds = calendar.getTimeInMillis() / 1000;

        String header = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        String payload = "{\"appKey\":\"" + apiKey + "\","+
                "\"name\":"+name+"," +
                "\"iat\":" + currentTimeInSeconds+ "," +
                "\"exp\":" + time30MinAheadInSeconds + "," +
                "\"tokenExp\":" + time30MinAheadInSeconds + "}";

        String base64UrlEncodedHeader = base64UrlEncode(header.getBytes(StandardCharsets.UTF_8));
        String base64UrlEncodedPayload = base64UrlEncode(payload.getBytes(StandardCharsets.UTF_8));

        String dataToSign = base64UrlEncodedHeader + "." + base64UrlEncodedPayload;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] signatureBytes = sha256_HMAC.doFinal(dataToSign.getBytes(StandardCharsets.UTF_8));
        String base64UrlEncodedSignature = base64UrlEncode(signatureBytes);

        return base64UrlEncodedHeader + "." + base64UrlEncodedPayload + "." + base64UrlEncodedSignature;
    } catch (NoSuchAlgorithmException | InvalidKeyException e) {
        e.printStackTrace();
        return null;
    }
}

    private static String base64UrlEncode(byte[] input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input);
    }
}
