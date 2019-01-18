package ir.maralani.flighttracker.listener;

import ir.maralani.flighttracker.model.Configuration;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.ConfigurationService;
import ir.maralani.flighttracker.service.UserSessionInformationService;
import ir.maralani.flighttracker.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserSessionInformationService userSessionInformationService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private EventService eventService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            request.getSession().setMaxInactiveInterval(configuration.getSessionTimeoutInSeconds());
            request.getSession().setAttribute("passwordComplexity", configuration.getPasswordComplexity().ordinal());
            request.getSession().setAttribute("passwordLength", configuration.getPasswordLength());
        }
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Action","Successful Login");
        descriptionMap.put("username", authentication.getName());

        userSessionInformationService.createUserSessionInformation(request.getSession().getId(), authentication.getName(), request.getRemoteAddr());
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), authentication.getName(), Event.ActionType.LOGIN_LOGOUT, Event.SubType.USER_LOGIN_LOGOUT, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (defaultSavedRequest != null) {
            String targetURL = defaultSavedRequest.getRedirectUrl();
            response.sendRedirect(targetURL);
        } else {
            response.sendRedirect("/");
        }
    }
}