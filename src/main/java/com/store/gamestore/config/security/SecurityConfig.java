package com.store.gamestore.config.security;


import com.store.gamestore.auth.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    String errorPage = "/error";
    String signInPage = "/sign-in";
    String signUpPage = "/sign-up";
    String accessDeniedPage = "/access-denied-page";

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(DataSource dataSource,
                          PasswordEncoder passwordEncoder) {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    void configureGlobal(
        AuthenticationManagerBuilder auth,
        LoginAuthenticationProvider authenticationProvider
    ) throws Exception {

        auth.authenticationProvider(authenticationProvider)
            .jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder)
            .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
            .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf()
            .csrfTokenRepository(new CookieCsrfTokenRepository())
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .authorizeRequests()
            .mvcMatchers(errorPage, signInPage, signUpPage)
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage(signInPage)
            .loginProcessingUrl(signInPage)
            .usernameParameter("user")
            .passwordParameter("password")
            .defaultSuccessUrl("/upload", true)
            .failureUrl(signInPage + "?error")
            .permitAll()
            .and()
            .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutUrl("/signout")
            .logoutSuccessUrl(signInPage + "?signout")
            .permitAll();

    }
}