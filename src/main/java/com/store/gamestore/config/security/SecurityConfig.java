package com.store.gamestore.config.security;


import com.store.gamestore.common.AppConstraints.Authentication;
import com.store.gamestore.common.AppConstraints.ExtendedAppPath;
import com.store.gamestore.common.auth.LoginAuthenticationProvider;
import com.store.gamestore.common.AppConstraints.AppPath;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  void configureGlobal(AuthenticationManagerBuilder auth,
      LoginAuthenticationProvider authenticationProvider) throws Exception {

    auth.authenticationProvider(authenticationProvider)
        .jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder)
        .usersByUsernameQuery(Authentication.GET_USER_BY_USERNAME_QUERY)
        .authoritiesByUsernameQuery(Authentication.GET_AUTHORITY_BY_USERNAME_QUERY);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        .csrf()
        .csrfTokenRepository(new CookieCsrfTokenRepository())
        .and()
        .authorizeRequests()
        .mvcMatchers(AppPath.ERROR_PAGE, ExtendedAppPath.API_PAGE, AppPath.LOG_IN_PAGE,
            AppPath.ACCOUNT_CREATION_PAGE, AppPath.STORE_PAGE, AppPath.START_PAGE,
            AppPath.ACCESS_DENIED_PAGE, ExtendedAppPath.GAME_PAGE)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage(AppPath.LOG_IN_PAGE)
        .loginProcessingUrl(AppPath.LOG_IN_PAGE)
        .defaultSuccessUrl(AppPath.PROFILE_PAGE, true)
        .failureUrl(AppPath.LOG_IN_PAGE + AppPath.ERROR_ATTRIBUTE)
        .permitAll();

  }
}