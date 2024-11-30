/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.security.jwt;

import com.windev.identity_service.constant.AuthConstant;
import com.windev.identity_service.service.cache.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        try{
            if(token != null && jwtTokenProvider.validateToken(token)){
                String username = jwtTokenProvider.getUsernameFromJWT(token);

                String redisKey = AuthConstant.TOKEN_PREFIX + username;
                String cachedToken = redisService.get(redisKey, String.class).get();

                if(cachedToken == null || !cachedToken.equals(token)){
                    log.warn("Token is invalid/ disabled.");
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(request,response);
                    return;
                }


                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch(Exception e){
            log.error("doFilterInternal() --> Unable to authenticate user: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
