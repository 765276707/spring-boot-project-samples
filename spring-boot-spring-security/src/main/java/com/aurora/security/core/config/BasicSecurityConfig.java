package com.aurora.security.core.config;

import com.aurora.security.core.filter.authc.JwtAuthorizationFilter;
import com.aurora.security.core.filter.authc.JwtManager;
import com.aurora.security.core.filter.limiter.IRateLimitProcessor;
import com.aurora.security.core.filter.limiter.RateLimitFilter;
import com.aurora.security.core.filter.replay_attack.IReplayAttackProcessor;
import com.aurora.security.core.filter.replay_attack.ReplayAttackFilter;
import com.aurora.security.core.filter.steal_link.IStealLinkListProvider;
import com.aurora.security.core.filter.steal_link.StealLinkFilter;
import com.aurora.security.core.filter.xss_attack.XssAttackFilter;
import com.aurora.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.annotation.Resource;

/**
 * Spring Security预设配置
 * @author xzbcode
 */
@Slf4j
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource
    private SecurityProperties securityProperties;
    @Resource
    private AuthenticationEntryPoint authcEntryPoint;
    @Resource
    private JwtManager jwtManager;
    @Resource
    private IRateLimitProcessor rateLimitProcessor;
    @Resource
    private IReplayAttackProcessor replayAttackProcessor;
    @Resource
    private IStealLinkListProvider stealLinkListProvider;


    protected void customConfigure(HttpSecurity http) throws Exception {
        // 默认配置
        http.authorizeRequests()
                // 放行 OPTIONS 请求 (跨域请求会用该请求进行预检)
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                // 禁用CSRF 和 Session
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 防止iframe造成跨域
                .and()
                .headers().frameOptions().disable()

                // 开启cors
                .and().cors()

                // 过滤器配置
                .and()
                .addFilterBefore(
                        new JwtAuthorizationFilter(userDetailsService, securityProperties, authcEntryPoint, jwtManager),
                        UsernamePasswordAuthenticationFilter.class)
                // 禁用默认的表单登录和登出
                // .formLogin().disable()
                .logout().disable()

                // 默认的未登录(认证失败)配置
                .exceptionHandling()
                .authenticationEntryPoint(authcEntryPoint);

        log.info(" -= (^-^) =- spring security add a custom filter : JwtAuthorizationFilter");

        // 根据配置参数决定是否配置的过滤器
        if (securityProperties.getRateLimit().getEnabled()) {
            log.info(" -= (^-^) =- spring security add a custom filter : RateLimitFilter");
            http.addFilterBefore(new RateLimitFilter(rateLimitProcessor), JwtAuthorizationFilter.class);
        }
        if (securityProperties.getReplayAttack().getEnabled()) {
            log.info(" -= (^-^) =- spring security add a custom filter : ReplayAttackFilter");
            http.addFilterBefore(new ReplayAttackFilter(replayAttackProcessor), JwtAuthorizationFilter.class);
        }
        if (securityProperties.getStealLink().getEnabled()) {
            log.info(" -= (^-^) =- spring security add a custom filter : StealLinkFilter");
            http.addFilterBefore(new StealLinkFilter(stealLinkListProvider), JwtAuthorizationFilter.class);
        }
        if (securityProperties.getXssAttack().getEnabled()) {
            log.info(" -= (^-^) =- spring security add a custom filter : XssAttackFilter");
            http.addFilterBefore(new XssAttackFilter(), JwtAuthorizationFilter.class);
        }
    }

    protected void configHttpSecurity(HttpSecurity http) throws Exception {
        log.info("start configure HttpSecurity property.");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 通用配置
        this.customConfigure(http);
        // 定制化配置
        this.configHttpSecurity(http);
    }
}
