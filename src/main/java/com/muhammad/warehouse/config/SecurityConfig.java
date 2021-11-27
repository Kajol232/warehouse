package com.muhammad.warehouse.config;

import com.muhammad.warehouse.config.jwt.JWTAuthEntryPoint;
import com.muhammad.warehouse.config.jwt.JWTFilter;

import com.muhammad.warehouse.service.IUserService;
import com.muhammad.warehouse.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private JWTFilter filter;
    private JWTAuthEntryPoint entryPoint;

    public SecurityConfig(JWTFilter filter, JWTAuthEntryPoint entryPoint) {
        this.filter = filter;
        this.entryPoint = entryPoint;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(ADMIN_WHITELIST).hasRole("ADMIN")
                .antMatchers(WORKER_USER_WHITELIST).hasRole("WORKER_USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/users/login",
            "/users/changePassword/",
            "/order/reportFromDate/{date}",
            "/orders/lists",
            "/stock/add",
    };

    private static final String[] ADMIN_WHITELIST = {
            "/users/lists",
            "/users/getById/{id}",
            "/users/register",
            "/users/update/{id}",
            "/users/resetPassword",
            "/users/delete/{id}",
            "/stocks/lists",
            "/stocks/restock/{id}",
            "/stocks/delete/{id}",
            "/orders/getById/{id}",
            "/orders/log",
            "/orders/getAllOrdersFromDate"

    };
    private static final String[]  WORKER_USER_WHITELIST = {
            "/stocks/lists",
            "/stocks/getById/{id}",
            "/orders/log",
            "/orders/getAllOrdersFromDate"



    };
}
