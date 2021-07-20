package com.aurora.security.core.util;


import com.aurora.security.core.model.JwtClaims;
import com.aurora.security.core.model.TokenType;
import io.jsonwebtoken.*;
import org.springframework.lang.NonNull;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT令牌工具类
 * @author xzbcode
 */
@SuppressWarnings("all")
public class JwtUtil {

    // 令牌建造器
    private final static JwtBuilder jwtBuilder = Jwts.builder();
    // 令牌解析器
    private final static JwtParser jwtParser = Jwts.parser();

    public final static String HEADER_TYP = "typ";
    public final static String HEADER_ALG = "alg";
    public final static String CLAIMS_TOKEN_TYPE = "ctt";

    // 算法
    private static SignatureAlgorithm sign_alg = SignatureAlgorithm.RS256;

    /**
     * 创建令牌
     * @param claims jwtBody
     * @param type 令牌类型
     * @param privateKey RSA256私钥
     * @return
     */
    public static String createToken(JwtClaims claims, TokenType type, @NonNull PrivateKey privateKey) {
        // 自定义属性
        Map<String, Object> dataMap = new HashMap<>(1);
        dataMap.put(CLAIMS_TOKEN_TYPE, type.getValue());
        // 构建
        jwtBuilder.setHeaderParam(HEADER_TYP, "jwt")
                .setHeaderParam(HEADER_ALG, sign_alg)
                .setClaims(dataMap)
                .setId(claims.getId())
                .setSubject(claims.getSubject())
                .setAudience(claims.getAudience())
                .setIssuedAt(claims.getIssuedAt())
                .setExpiration(claims.getExpiration())
                .signWith(sign_alg, privateKey);
        return jwtBuilder.compact();
    }

    /**
     * 刷新令牌
     * @param refreshToken 刷新的令牌
     * @param type 令牌类型
     * @param publicKey RSA256公钥
     * @param privateKey RSA256私钥
     * @return
     */
    public static String refreshToken(String refreshToken, TokenType type,
                                      @NonNull PublicKey publicKey, @NonNull PrivateKey privateKey) {
        // 解析刷新令牌
        JwtClaims claims = getJwtClaims(refreshToken, type, publicKey);
        // 构建
        return createToken(claims, type, privateKey);
    }


    /**
     * 获取JwtClaims
     * @param token
     * @param publicKey
     * @return
     */
    public static JwtClaims getJwtClaims(String token, @NonNull TokenType type, @NonNull PublicKey publicKey) {
        Claims body = jwtParser
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
        // 判断该令牌的类型是否匹配，匹配失败抛出异常
        if (!type.getValue().equals(body.get(CLAIMS_TOKEN_TYPE))) {
            throw new UnsupportedJwtException(
                    String.format("error: this token is not a %s token.", type.getValue()));
        }
        // 匹配成功，则返回解析结果
        return new JwtClaims(body);
    }


    /**
     * 获取过期时间
     * @param token
     * @param publicKey
     * @return
     */
    public static Date getExpiration(String token, TokenType type, @NonNull PublicKey publicKey) {
        return getJwtClaims(token, type, publicKey).getExpiration();
    }

}
