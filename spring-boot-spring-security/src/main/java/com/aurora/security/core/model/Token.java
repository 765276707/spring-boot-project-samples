package com.aurora.security.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 认证令牌
 * @author xzbcode
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Token implements Serializable {

    private String accessToken;

    private String tokenType;

    private String refreshToken;

    private String scope;

    private Long expiresIn;


    public Token(String accessToken, TokenType type, String refreshToken, String scope, Long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = type.getValue();
        this.refreshToken = refreshToken;
        this.scope = scope;
        this.expiresIn = expiresIn;
    }

    public Builder builder() {
        return new Builder();
    }

    /**
     * <h1>建造者</h1>
     * @author xzb
     */
    public static class Builder {
        private String accessToken;
        private String tokenType;
        private String refreshToken;
        private String scope;
        private Long expiresIn;

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder scope(String scope) {
            this.scope = scope;
            return this;
        }

        public Builder expiresIn(Long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public Token build() {
            return new Token(this.accessToken, this.tokenType, this.refreshToken, this.scope, this.expiresIn);
        }

    }
}
