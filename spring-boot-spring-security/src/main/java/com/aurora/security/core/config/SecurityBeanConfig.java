package com.aurora.security.core.config;

import com.aurora.security.core.filter.authc.DefaultJwtManager;
import com.aurora.security.core.filter.authc.JwtManager;
import com.aurora.security.core.filter.limiter.CacheRateLimitProcessor;
import com.aurora.security.core.filter.limiter.IRateLimitProcessor;
import com.aurora.security.core.filter.replay_attack.CacheReplayAttackProcessor;
import com.aurora.security.core.filter.replay_attack.IReplayAttackProcessor;
import com.aurora.security.core.filter.steal_link.IStealLinkListProvider;
import com.aurora.security.core.filter.steal_link.PropertiesStealLinkListProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Security 一些组件默认初始化到容器
 * @author xzbcode
 */
@Configuration
public class SecurityBeanConfig {

    @Bean
    @ConditionalOnMissingBean(JwtManager.class)
    public JwtManager jwtManager() {
        return new DefaultJwtManager();
    }

    @Bean
    @ConditionalOnMissingBean(IRateLimitProcessor.class)
    public IRateLimitProcessor rateLimitProcessor() {
        return new CacheRateLimitProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(IReplayAttackProcessor.class)
    public IReplayAttackProcessor replayAttackProcessor() {
        return new CacheReplayAttackProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(IStealLinkListProvider.class)
    public IStealLinkListProvider stealLinkListProvider() {
        return new PropertiesStealLinkListProvider();
    }
}
