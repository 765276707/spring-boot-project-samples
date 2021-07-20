package com.aurora.security.core.model;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * JWT的属性值d对象
 * @author xzbcode
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtClaims implements Serializable {

    // body
    private String id;
    private String subject;
    private String audience;
    private Date issuedAt;
    private Date expiration;

    public JwtClaims(Claims claims) {
        this.id = claims.getId();
        this.subject = claims.getSubject();
        this.audience = claims.getAudience();
        this.issuedAt = claims.getIssuedAt();
        this.expiration = claims.getExpiration();
    }
}
