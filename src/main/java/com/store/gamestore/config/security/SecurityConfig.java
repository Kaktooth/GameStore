package com.store.gamestore.config.security;


import com.store.gamestore.auth.Authority;
import com.store.gamestore.auth.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
            .usersByUsernameQuery("SELECT username, password, enabled, email FROM users WHERE username = ? OR email = ?")
            .authoritiesByUsernameQuery("SELECT username, email, authority FROM authorities WHERE username = ? OR email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionAuthenticationStrategy(new SessionFixationProtectionStrategy())
            .and()
            .authorizeRequests()
            .mvcMatchers("/error", "/sign-in", "/sign-up")
            .permitAll()
            .mvcMatchers("/admin-page")
            .hasAuthority(Authority.ADMIN.getNumVal().toString())
            .anyRequest().authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .loginPage("/sign-in")
            .loginProcessingUrl("/sign-in")
            .usernameParameter("user")
            .passwordParameter("password")
            .defaultSuccessUrl("/upload", true)
            .failureUrl("/sign-in?error")
            .permitAll()
            .and()
            .logout()
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .logoutUrl("/signout")
            .logoutSuccessUrl("/sign-in?signout")
            .permitAll()
            .and()
            .exceptionHandling().accessDeniedPage("/access-denied-page");

    }
}