package ir.mfava.modfava.pardazesh.config;

import ir.mfava.modfava.pardazesh.listener.CustomAuthenticationFailureHandler;
import ir.mfava.modfava.pardazesh.listener.CustomAuthenticationSuccessHandler;
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
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        customAuthenticationSuccessHandler.setUseReferer(true);

        http
                .authorizeRequests()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/icons/**").permitAll()
                .antMatchers("/webix/**").permitAll()
                .antMatchers("/persian-datepicker/**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/map/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/base-info/station/**").hasRole("WEATHER_STATION_MANAGER")
                .antMatchers("/base-info/user/**").hasRole("USER_MANAGER")
                .antMatchers("/base-info/grant/**").hasRole("ACCESS_MANAGER")
                .antMatchers("/base-info/phenomena/**").hasRole("EVENTS_MANAGER")
                .antMatchers("/base-info/unit/**").hasRole("UNITS_MANAGER")
                .antMatchers("/base-info/files/**").hasRole("FILES_MANAGER")
                .antMatchers("/base-info/update/**").hasRole("UPDATE_DATA")
                .antMatchers("/bulletin/**").hasRole("VIEW_BULLETIN")
                .antMatchers("/message/**").hasRole("USER")
                .antMatchers("/message/save").hasRole("MESSAGE_SENDER")
                .antMatchers("/defactor/**").hasRole("VIEW_DEFACTOR")
                .antMatchers("/bulletin/**").hasRole("ADMIN")
                .antMatchers("/role/**").hasRole("ACCESS_MANAGER")
                .antMatchers("/history/**").hasRole("VIEW_LOGIN_HISTORY")
                .antMatchers("/upload/**").hasRole("UPLOAD_FILES")
                .antMatchers("/report/**").hasRole("REPORT")
                .antMatchers("/sec/**").anonymous()
                .antMatchers("/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf();

        http.csrf().ignoringAntMatchers("/sec/**");
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
