package ir.mfava.modfava.pardazesh.controller;


import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

/**
 * @author Drago
 */
public class BaseController {
    @Autowired
    private UserService userService;

    public User getUser(Authentication authentication) {
        return userService.findByUsername(((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername());
    }

    public Long getUserId(Authentication authentication) {
        return getUser(authentication).getId();
    }

}
