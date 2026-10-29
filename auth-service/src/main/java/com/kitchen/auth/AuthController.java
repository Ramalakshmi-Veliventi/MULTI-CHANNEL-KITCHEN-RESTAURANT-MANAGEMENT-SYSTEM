package com.kitchen.auth;
import com.kitchen.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;
@RestController
@RequestMapping("/auth")
public class AuthController {
 @PostMapping("/login")
 public Map<String, Object> login(@RequestBody LoginRequest request) {
   if (!"admin".equals(request.username()) || !"admin123".equals(request.password())) {
     throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
   }
   return Map.of("token", JwtUtil.createToken(request.username()), "tokenType", "Bearer", "expiresInSeconds", 3600);
 }
 public record LoginRequest(String username, String password) {}
}
