package com.aurora.security.core.filter.authc;

import com.aurora.security.core.properties.SecurityProperties;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * JWT 过滤器
 * @author xzbcode
 */
@Slf4j
@SuppressWarnings("all")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;
    private final AuthenticationEntryPoint authcEntryPoint;
    private final JwtManager jwtManager;

    // 没有配置token key则默认使用该值
    private final static String DEFAULT_TOKEN_KEY_IN_HEADER = "Authorization";

    public JwtAuthorizationFilter(UserDetailsService userDetailsService,
                                  SecurityProperties securityProperties,
                                  AuthenticationEntryPoint authcEntryPoint,
                                  JwtManager jwtManager) {
        this.userDetailsService = userDetailsService;
        this.securityProperties = securityProperties;
        this.authcEntryPoint = authcEntryPoint;
        this.jwtManager = jwtManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        // 获取令牌
        String token = this.obtainTokenFromHeader(request);
        if (!StringUtils.isEmpty(token)) {
            // 校验令牌
            try {
                String username = jwtManager.getUsernameFromAccessToken(token);
                if (!StringUtils.isEmpty(username)) {
                    // 查询用户权限
                    UserDetails user = userDetailsService.loadUserByUsername(username);
                    // 当前上下文中未存在该用户的认证信息, 重新保存认证信息
                    boolean existAuthentication = SecurityContextHolder.getContext().getAuthentication()!=null;
                    if (null!=user && !existAuthentication) {
                        this.buildAuthenticationInSecurityContext(user, request);
                    }
                }
            } catch (JwtException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                // 令牌过期或无效
                authcEntryPoint.commence(request, response, null);
                return;
            }
        }

        // 执行下一个过滤器
        filterChain.doFilter(request, response);
    }


    /**
     * <h1>从请求头中获取令牌</h1>
     * @param request
     * @return
     */
    protected String obtainTokenFromHeader(HttpServletRequest request) {
        // 获取令牌配置信息
        String tokenKey = this.securityProperties.getToken().getHeader();
        if (StringUtils.isEmpty(tokenKey)) {
            tokenKey = DEFAULT_TOKEN_KEY_IN_HEADER;
        }
        // 从Header中获取令牌
        return request.getHeader(tokenKey);
    }


    /**
     * <h1>构建认证信息</h1>
     * @param user
     * @param request
     */
    protected void buildAuthenticationInSecurityContext(UserDetails user, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
