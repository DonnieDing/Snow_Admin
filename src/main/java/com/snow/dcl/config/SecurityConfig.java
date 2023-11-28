package com.snow.dcl.config;

import com.snow.dcl.constant.SpringSecurityConstant;
import com.snow.dcl.handler.JwtTokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtTokenAuthenticationFilter authenticationFilter() {
        return new JwtTokenAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 配置白名单: 没有权限也可以访问的资源
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .mvcMatchers(SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS);
    }

    // security核心配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //请求都需要进行认证之后才能访问，除白名单以外的资源
        http.authorizeRequests()
                .antMatchers("/user/login", "/captcha/*", "/user/minaLogin").permitAll()
                .anyRequest().authenticated()
                .and()

                //添加jwt token登录授权的过滤器，校验token
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //添加未授权和未登录的返回结果
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
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

    }

}
