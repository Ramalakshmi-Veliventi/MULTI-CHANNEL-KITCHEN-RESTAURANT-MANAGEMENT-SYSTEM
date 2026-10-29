package com.kitchen.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
 @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
   throws ServletException, IOException {
   String path = request.getRequestURI();
   if (path.startsWith("/actuator/health") || path.startsWith("/actuator/info")) { chain.doFilter(request, response); return; }
   String header = request.getHeader("Authorization");
   if (header == null || !header.startsWith("Bearer ") || !JwtUtil.isValid(header.substring(7))) {
     response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     response.setContentType("application/json");
     response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Provide a valid Bearer JWT token\"}");
     return;
   }
   chain.doFilter(request, response);
 }
}
