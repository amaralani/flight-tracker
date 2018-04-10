package ir.mfava.modfava.pardazesh.listener;

import ir.mfava.modfava.pardazesh.model.Configuration;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.ConfigurationService;
import ir.mfava.modfava.pardazesh.service.LoginFailureLogService;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
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
    @Autowired
    private EventService eventService;

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
        request.getSession().setAttribute("clientIP", request.getRemoteAddr());
        request.getSession().setAttribute("clientHostName", request.getRemoteHost());
        loginFailureLogService.createLog(username, request.getRemoteAddr());
        eventService.addEvent(request.getRemoteAddr(),request.getRemoteHost(),request.getRequestURI(),request.getParameter("username"), Event.ActionType.LOGIN_LOGOUT, Event.SubType.UNSUCCESS_LOGIN, Event.Flag.FAILURE,null, Event.Sensitivity.NOTIFICATION);
        Long countUserLoginFailure = loginFailureLogService.countUserLogsSinceLastLogin(username);
        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            if (configuration.getLoginFailureAccountLockCount() <= countUserLoginFailure) {
                User user = userService.findByUsername(username);
                if (user != null) {
                    user.setLocked(true);
                    userService.save(user);
                    eventService.addEvent(request.getRemoteAddr(),request.getRemoteHost(),request.getRequestURI(),request.getParameter("username"), Event.ActionType.USER_MANAGEMENT, Event.SubType.BLOCK_USER, Event.Flag.FAILURE,null, Event.Sensitivity.ALARM);
                }
            }
        }
        response.sendRedirect("/");
    }
}