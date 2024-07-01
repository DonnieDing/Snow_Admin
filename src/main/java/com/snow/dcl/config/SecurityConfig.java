package com.snow.dcl.config;

import com.snow.dcl.constant.SpringSecurityConstant;
import com.snow.dcl.handler.JwtTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationEntryPoint authenticationEntryPoint,
                          JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter,
                          AuthenticationConfiguration authenticationConfiguration) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * 在security安全框架中，提供了若干密码解析器实现类型。
     * 其中BCryptPasswordEncoder 叫强散列加密。可以保证相同的明文，多次加密后，
     * 密码有相同的散列数据，而不是相同的结果。
     * 匹配时，是基于相同的散列数据做的匹配。
     * Spring Security 推荐使用 BCryptPasswordEncoder 作为密码加密和解析器。
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 放行资源
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS);
    }

    // security核心配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> {
                    try {
                        authorize
                                // 放行登录接口
                                .requestMatchers("/user/login", "/captcha/*", "/user/minaLogin").permitAll()
                                // 其余的都需要权限校验
                                .anyRequest().authenticated()
                                .and()
                                //添加jwt token登录授权的过滤器，校验token
                                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                                //添加未授权和未登录的返回结果
                                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                                // 防跨站请求伪造
                                .and()
                                /**
                                 * 基于token认证，关闭session，此处若关闭session，
                                 * 登录认证后，不会在SecurityContextHolder.getContext().getAuthentication()中存储authentication，
                                 * 直接从上下文获取用户信息为null，可使用缓存处理token生效问题。
                                 */
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                //使用jwt，关闭csrf跨域和跨域
                                .cors().and().csrf().disable();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).build();
    }
}
