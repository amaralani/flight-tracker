package ir.mfava.modfava.pardazesh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/webix/**").permitAll()
                .antMatchers("/persian-datepicker/**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/map/**").hasRole("USER")
                .antMatchers("/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/base-info/station/**").hasRole("WEATHER_STATION_MANAGER")
                .antMatchers("/base-info/user/**").hasRole("USER_MANAGER")
                .antMatchers("/base-info/grant/**").hasRole("ACCESS_MANAGER")
                .antMatchers("/user/manage/**").hasRole("USER_MANAGER")
                .antMatchers("/base-info/phenomena/**").hasRole("EVENTS_MANAGER")
                .antMatchers("/base-info/unit/**").hasRole("UNITS_MANAGER")
                .antMatchers("/bulletin/**").hasRole("VIEW_BULLETIN")
//                .antMatchers("/bulletin/**").hasRole("ADMIN") TODO: add access control for adding bulletin
                .antMatchers("/defactor/**").hasRole("VIEW_DEFACTOR")
//                .antMatchers("/defactor/**").hasRole("ADMIN") TODO: add access control for adding defactor
                .antMatchers("/bulletin/**").hasRole("ADMIN")
                .antMatchers("/role/**").hasRole("ACCESS_MANAGER")
                .antMatchers("/history/**").hasRole("VIEW_LOGIN_HISTORY")
                .antMatchers("/upload/**").hasRole("UPLOAD_FILES")
                .antMatchers("/report/**").hasRole("REPORT")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf();

        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
