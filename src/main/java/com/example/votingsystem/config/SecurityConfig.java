package com.example.votingsystem.config;

import com.example.votingsystem.filter.IpWhitelistFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final IpWhitelistFilter ipWhitelistFilter;

    public SecurityConfig(IpWhitelistFilter ipWhitelistFilter) {
        this.ipWhitelistFilter = ipWhitelistFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 自定义IP白名单过滤器
                .addFilterBefore(ipWhitelistFilter, UsernamePasswordAuthenticationFilter.class)
                // 禁用 CSRF（方便 API 测试）
                .csrf().disable()
                .authorizeRequests()
                // 公共接口
                .antMatchers("/api/login", "/h2-console/**", "/index.html").permitAll()
                .antMatchers(HttpMethod.GET, "/api/votes/**").permitAll()
                // 用户接口
                .antMatchers(HttpMethod.POST, "/api/votes/**").hasRole("USER")
                // 管理员接口
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                // 其他请求都需要认证
                .anyRequest().authenticated()
                .and()
                // 登录配置
                .formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler((req, res, auth) -> res.setStatus(200))
                .failureHandler((req, res, ex) -> res.setStatus(401))
                .permitAll()
                .and()
                // 登出配置
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                .permitAll()
                .and()
                // H2 console 允许 iframe
                .headers().frameOptions().sameOrigin();
    }
}
