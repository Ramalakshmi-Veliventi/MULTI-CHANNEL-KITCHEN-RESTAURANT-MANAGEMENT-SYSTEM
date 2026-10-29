package com.kitchen.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public final class JwtUtil {
    private static final String SECRET = "local-demo-secret-change-this-before-production-123";
    private static final Base64.Encoder URL = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder URL_DECODER = Base64.getUrlDecoder();
    private JwtUtil() {}

    public static String createToken(String username) {
        long expiry = Instant.now().getEpochSecond() + 3600;
        String header = URL.encodeToString("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
        String payload = URL.encodeToString(("{\"sub\":\"" + username + "\",\"exp\":" + expiry + "}").getBytes(StandardCharsets.UTF_8));
        String unsigned = header + "." + payload;
        return unsigned + "." + URL.encodeToString(sign(unsigned));
    }

    public static boolean isValid(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return false;
            String unsigned = parts[0] + "." + parts[1];
            byte[] expected = sign(unsigned);
            byte[] actual = URL_DECODER.decode(parts[2]);
            if (!java.security.MessageDigest.isEqual(expected, actual)) return false;
            String payload = new String(URL_DECODER.decode(parts[1]), StandardCharsets.UTF_8);
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("\"exp\"\\s*:\\s*(\\d+)").matcher(payload);
            return m.find() && Long.parseLong(m.group(1)) > Instant.now().getEpochSecond();
        } catch (Exception ex) { return false; }
    }

    private static byte[] sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception ex) { throw new IllegalStateException(ex); }
    }
}
