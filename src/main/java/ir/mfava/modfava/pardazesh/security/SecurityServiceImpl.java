package ir.mfava.modfava.pardazesh.security;


import ir.mfava.modfava.pardazesh.model.Configuration;
import ir.mfava.modfava.pardazesh.model.Role;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.repository.UserRepository;
import ir.mfava.modfava.pardazesh.service.ConfigurationService;
import ir.mfava.modfava.pardazesh.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional(transactionManager = "transactionManager")
@Service
public class SecurityServiceImpl implements SecurityService{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autologin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }

    @Service
    public static class UserDetailsServiceImpl implements UserDetailsService{
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ConfigurationService configurationService;

        @Override
        @Transactional(readOnly = true)
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userRepository.findByUsername(username);
            List<Configuration> configurationList = configurationService.getAll();
            if (!configurationList.isEmpty()) {
                Configuration configuration = configurationList.get(0);
                if (DateUtils.differenceInDays(user.getPasswordUpdatedDate(), new Date()) >= configuration.getPasswordResetPeriod()) {
                    user.setPasswordExpired(true);
                    userRepository.save(user);
                }
            }
            if(!user.getEnabled()) throw new RuntimeException("disabled");
            if(user.getPasswordExpired()) throw new RuntimeException("passwordExpired");
            if(user.getLocked()) throw new RuntimeException("locked");

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : user.getRoles()){
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true,grantedAuthorities);
        }
    }
}
