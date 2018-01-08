package ir.mfava.modfava.pardazesh.listener;

import ir.mfava.modfava.pardazesh.model.Configuration;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.service.ConfigurationService;
import ir.mfava.modfava.pardazesh.service.LoginFailureLogService;
import ir.mfava.modfava.pardazesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private LoginFailureLogService loginFailureLogService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        switch (exception.getMessage()) {
            case "disabled":
                request.getSession().setAttribute("loginError", "account-disabled");
                break;
            case "passwordExpired":
                request.getSession().setAttribute("loginError", "password-expired");
                break;
            case "locked":
                request.getSession().setAttribute("loginError", "account-locked");
                break;
            default:
                request.getSession().setAttribute("loginError", "login-error");
        }
        String username = request.getParameter("username");
        loginFailureLogService.createLog(username, request.getRemoteAddr());
        Long countUserLoginFailure = loginFailureLogService.countUserLogsSinceLastLogin(username);
        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            if (configuration.getLoginFailureAccountLockCount() <= countUserLoginFailure) {
                User user = userService.findByUsername(username);
                if (user != null) {
                    user.setLocked(true);
                    userService.save(user);
                }
            }
        }
        response.sendRedirect("/");
    }
}