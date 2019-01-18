package ir.maralani.flighttracker.security;

import ir.maralani.flighttracker.repository.report.event.EventRepository;
import ir.maralani.flighttracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author Maralani
 * 3/10/2018
 */
@Service
public class AuditorAwareServiceImpl implements AuditorAware<Long> {

    @Autowired
    private UserService userService;
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Long getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return userService.findByUsername(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername()).getId();
    }
}
