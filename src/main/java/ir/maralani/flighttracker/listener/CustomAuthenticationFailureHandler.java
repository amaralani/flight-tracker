package ir.maralani.flighttracker.listener;

import ir.maralani.flighttracker.model.Configuration;
import ir.maralani.flighttracker.model.User;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.ConfigurationService;
import ir.maralani.flighttracker.service.LoginFailureLogService;
import ir.maralani.flighttracker.service.UserService;
import ir.maralani.flighttracker.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Action","Unsuccessful Login");
        descriptionMap.put("username", username);


        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), request.getParameter("username"), Event.ActionType.LOGIN_LOGOUT, Event.SubType.UNSUCCESS_LOGIN, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.NOTIFICATION);
        Long countUserLoginFailure = loginFailureLogService.countUserLogsSinceLastLogin(username);
        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            if (configuration.getLoginFailureAccountLockCount() <= countUserLoginFailure) {
                User user = userService.findByUsername(username);
                if (user != null) {
                    user.setLocked(true);
                    userService.save(user);
                    descriptionMap.put("Action","Block User");
                    descriptionMap.put("username", username);
                    eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(), request.getRemoteHost(), request.getRequestURI(), request.getParameter("username"), Event.ActionType.USER_MANAGEMENT, Event.SubType.BLOCK_USER, Event.Flag.FAILURE, descriptionMap, Event.Sensitivity.ALARM);
                }
            }
        }
        response.sendRedirect("/");
    }
}