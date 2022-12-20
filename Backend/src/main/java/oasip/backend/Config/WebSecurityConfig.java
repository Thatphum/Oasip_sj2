package oasip.backend.Config;

import oasip.backend.Exception.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.*;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
//        firewall.setAllowUrlEncodedSlash(true);
//        return firewall;
        return new DefaultHttpFirewall();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST , "/api/auth/match").permitAll()
                .antMatchers(HttpMethod.GET , "/api/auth/refreshtoken").hasAnyAuthority("student","admin" , "lecturer")
                .antMatchers(HttpMethod.GET , "/api/users/**").hasAnyAuthority("admin" )
                .antMatchers(HttpMethod.POST , "/api/users/**").permitAll()
                .antMatchers(HttpMethod.PATCH , "/api/users/**").hasAnyAuthority("admin" )
                .antMatchers(HttpMethod.DELETE , "/api/users/**").hasAnyAuthority("admin" )
//                .antMatchers("/api/users/**").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.GET , "/api/events").hasAnyAuthority("student","admin" , "lecturer")
                .antMatchers(HttpMethod.GET , "/api/events/filter/**").hasAnyAuthority("student","admin" , "lecturer")
                .antMatchers(HttpMethod.POST , "/api/events/**").permitAll()
                .antMatchers(HttpMethod.PATCH , "/api/events/**").hasAnyAuthority("student","admin")
                .antMatchers(HttpMethod.DELETE , "/api/events/**").hasAnyAuthority("student","admin")
                .antMatchers(HttpMethod.GET , "/api/file/**").permitAll()
                .antMatchers(HttpMethod.POST , "/api/file/**").permitAll()
                .antMatchers(HttpMethod.DELETE , "/api/file/**").permitAll()
                .antMatchers(HttpMethod.GET , "/api/categories/**").permitAll()
                .antMatchers(HttpMethod.PATCH , "/api/categories/**").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler()).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }
}