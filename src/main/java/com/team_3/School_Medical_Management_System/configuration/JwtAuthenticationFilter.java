package com.team_3.School_Medical_Management_System.configuration;

import com.team_3.School_Medical_Management_System.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
  //  private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;

    }

    @Override
    // Bảo vệ cổng kiểm tra thẻ nhân viên
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
     // 1. Lấy thẻ nhân viên từ túi
    String jwt = parseJwt(request); // Giống như lấy thẻ ID từ ví


        // 2. Kiểm tra thẻ có hợp lệ không
    if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        
        // 3. Đọc thông tin trên thẻ
        String username = jwtUtils.getUsernameFromJwtToken(jwt);      // Tên: "Nguyễn Văn A"
        List<String> roles = jwtUtils.getRolesFromJwtToken(jwt);      // Chức vụ: ["ROLE_NURSE"]
            
        
        
        if (roles != null) {
             // 4. Tạo phiếu thông hành nội bộ
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }

            // 5. Ghi nhận: "Người này được phép vào"
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // Có thể log cảnh báo ở đây nếu muốn
            }
        }
         // 6.Chuyển tiếp đến bảo vệ tiếp theo
        chain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }


}
