package com.example.rentaperson.config;

import com.example.rentaperson.service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .authorizeRequests()

                .antMatchers("/api/v1/user/register","/api/v1/user/viewAllPersons","/api/v1/user/viewByCategory/{cat}","/api/v1/user/viewByCity/{city}","/api/v1/review/reviewByPerson/{username}","/api/v1/review/aveOfPerson/{username}","/api/v1/user/viewByCategoryWithRate/{cat}","/api/v1/user/username/{id}","/api/v1/user/personByUsername/{username}").permitAll()
                .antMatchers("/api/v1/user/viewAll").hasAuthority("ADMIN")
                .antMatchers("/api/v1/skill/viewAllSkills").hasAuthority("ADMIN")
                .antMatchers("/api/v1/appointment/viewAll").hasAuthority("ADMIN")
                .antMatchers("/api/v1/appointment/post").hasAuthority("USER")
                .antMatchers("/api/v1/appointment/confirm/{id}").hasAuthority("PERSON")
                .antMatchers("/api/v1/appointment/complete/{id}").hasAuthority("PERSON")
                .antMatchers("/api/v1/appointment/update").hasAuthority("ADMIN")
                .antMatchers("/api/v1/appointment/delete").hasAuthority("ADMIN")
                .antMatchers("/api/v1/review/viewAll").hasAuthority("ADMIN")
                .antMatchers("/api/v1/review//addReview/{username}").hasAuthority("USER")
                .antMatchers("/api/v1/review/update").hasAuthority("ADMIN")
                .antMatchers("/api/v1/review/delete/{id}").hasAuthority("ADMIN")


                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/user/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .httpBasic().authenticationEntryPoint(entryPoint());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "OPTIONS"));
        configuration.addAllowedMethod(HttpMethod.TRACE);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Bean
    public AuthenticationEntryPoint entryPoint() {
        return new BasicAuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException authException) throws IOException {
                JSONObject jsonObject = new JSONObject();
                try {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    jsonObject.put("message", authException.getMessage());
                    response.getWriter()
                            .println(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterPropertiesSet() {
                setRealmName("Contact-Keeper");
                super.afterPropertiesSet();
            }
        };
    }

}
