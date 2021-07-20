package com.aurora.security.core.filter.authc;

import cn.hutool.core.date.DateUtil;
import com.aurora.security.core.cipher.RsaUtil;
import com.aurora.security.core.model.JwtClaims;
import com.aurora.security.core.model.Token;
import com.aurora.security.core.model.TokenType;
import com.aurora.security.core.model.User;
import com.aurora.security.core.properties.SecurityProperties;
import com.aurora.security.core.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;

/**
 * 默认的令牌管理器
 * @author xzbcode
 */
@Slf4j
public class DefaultJwtManager implements JwtManager {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public String getUsernameFromAccessToken(String accessToken)
                    throws InvalidKeySpecException, NoSuchAlgorithmException {
        JwtClaims jwtClaims = JwtUtil.getJwtClaims(accessToken, TokenType.ACCESS_TOKEN, this.getPublicKey());
        return jwtClaims.getSubject();
    }

    @Override
    public JwtClaims getClaimsFromRefreshToken(String refreshToken)
                    throws InvalidKeySpecException, NoSuchAlgorithmException {
        return JwtUtil.getJwtClaims(refreshToken, TokenType.REFRESH_TOKEN, this.getPublicKey());
    }

    @Override
    public Token createToken(@NonNull User user) {
        // 获取属性
        SecurityProperties.TokenProperties tokenProp = securityProperties.getToken();
        // 密钥
        PrivateKey privateKey = null;
        try {
            privateKey = this.getPrivateKey();
        } catch (InvalidKeySpecException| NoSuchAlgorithmException e) {
            if (log.isDebugEnabled()) {
                log.debug("create application authentication token failure, cause by : {}", e.getMessage());
            }
            throw new JwtSecurityException("error: create application authentication token failure.");
        }
        // 生成访问令牌
        JwtClaims accessClaims = this.buildClaims(user, TokenType.ACCESS_TOKEN, tokenProp);
        String accessToken = JwtUtil.createToken(accessClaims, TokenType.ACCESS_TOKEN, privateKey);
        // 生成刷新令牌
        JwtClaims refreshClaims = this.buildClaims(user, TokenType.ACCESS_TOKEN, tokenProp);
        String refreshToken = JwtUtil.createToken(refreshClaims, TokenType.REFRESH_TOKEN, privateKey);
        // 返回令牌
        return new Token(accessToken,
                        tokenProp.getType(),
                        refreshToken,
                        tokenProp.getScope(),
                        (long) tokenProp.getAccessTokenExpire());
    }

    @Override
    public Token refreshToken(@NonNull User user) {
        return this.createToken(user);
    }


    private JwtClaims buildClaims(@NonNull User user, TokenType type,
                                  SecurityProperties.TokenProperties tokenProp) {
        // 计算时间
        Date issuedAt = new Date();
        Date expiration = null;
        if (type.equals(TokenType.ACCESS_TOKEN)) {
            expiration = DateUtil.offsetSecond(issuedAt, tokenProp.getAccessTokenExpire());
        } else {
            expiration = DateUtil.offsetSecond(issuedAt, tokenProp.getRefreshTokenExpire());
        }
        return new JwtClaims(
                UUID.randomUUID().toString(),
                user.getUsername(),
                tokenProp.getAudience(),
                issuedAt,
                expiration
        );
    }

    /**
     * 获取令牌公钥
     * @return
     */
    private PublicKey getPublicKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PublicKey(securityProperties.getToken().getRsaPublicKey());
    }

    /**
     * 获取令牌私钥
     * @return
     */
    private PrivateKey getPrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return RsaUtil.string2PrivateKey(securityProperties.getToken().getRsaPrivateKey());
    }
}
