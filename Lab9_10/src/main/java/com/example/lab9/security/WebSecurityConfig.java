package com.example.lab9.security;


import com.example.lab9.jwt.JwtTokenAuthFilter;
import com.example.lab9.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService) // cung cấp userService cho SpringSecurity
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtTokenAuthFilter jwtTokenAuthFilter() {
        return new JwtTokenAuthFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // TODO Auto-generated method stub
        return super.authenticationManagerBean();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http
            .cors().and()
            .csrf().disable() // Tắt CSRF protection 
            .authorizeRequests()
            .antMatchers("/api/account/register", "/api/account/login").permitAll()
            .antMatchers(HttpMethod.GET, "/api/products", "/api/products/*").permitAll()
            .anyRequest().authenticated();


        //http.addFilterBefore(new JwtTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    

    
}
