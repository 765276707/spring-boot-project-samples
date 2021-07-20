package com.aurora.security.core.filter.authc;

import com.aurora.security.core.model.JwtClaims;
import com.aurora.security.core.model.Token;
import com.aurora.security.core.model.User;
import org.springframework.lang.NonNull;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 令牌管理器接口
 * @author xzbcode
 */
public interface JwtManager {

    /** 从访问令牌中获取用户凭证 */
    String getUsernameFromAccessToken(String accessToken) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /** 从刷新令牌中获取令牌信息 */
    JwtClaims getClaimsFromRefreshToken(String refreshToken) throws InvalidKeySpecException, NoSuchAlgorithmException;

    /** 创建令牌 */
    Token createToken(@NonNull User user);

    /** 刷新令牌 */
    Token refreshToken(@NonNull User user);

}
