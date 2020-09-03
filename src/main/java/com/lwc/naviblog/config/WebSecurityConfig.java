package com.lwc.naviblog.config;


import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.filter.CustomAuthenticationFilter;
import com.lwc.naviblog.handler.BlogAuthenticationFailureHandler;
import com.lwc.naviblog.handler.BlogAuthenticationSuccessHandler;
import com.lwc.naviblog.handler.BlogLogoutSuccessHandler;
import com.lwc.naviblog.service.SysUserService;
import com.lwc.naviblog.utils.CommonResultUtil;
import com.lwc.naviblog.utils.JsonResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                //处理跨域请求中的Preflight请求
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

        http.authorizeRequests()
                .antMatchers("http://localhost:8080/#/login", "/admin/login.html",
                        "/admin/login", "/css/**",
                        "/images/**", "/plugins/**",
                        "/js/**","/lib/**",
                        "/admin/types.html","/admin/tags.html",
                        "/admin/tag-input.html","/admin/type-input.html",
                        "/admin/blogs.html","/admin/blogs-input.html")
                .permitAll()
                .antMatchers("/admin/**")
                .authenticated();

        http.formLogin()
                .loginProcessingUrl("/admin/login")
                .permitAll();

        http.logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessHandler(new BlogLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .permitAll();

        http.exceptionHandling()
//                .accessDeniedHandler((request, response, authentication) -> JsonResponseUtil.writeObject(CommonResultUtil.error("权限不足！", null).setCode(403), response))
                .authenticationEntryPoint((req, resp, e) -> {
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    JsonResponseUtil.writeObject(CommonResultUtil.build(StatusCode.UNAUTHORIZED, "请先登录", null), resp);
                });


        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new BlogAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new BlogAuthenticationFailureHandler());
        filter.setFilterProcessesUrl("/admin/login");
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
