package ir.mfava.modfava.pardazesh.listener;

import ir.mfava.modfava.pardazesh.model.Configuration;
import ir.mfava.modfava.pardazesh.service.ConfigurationService;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.service.UserSessionInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserSessionInformationService userSessionInformationService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        List<Configuration> configurationList = configurationService.getAll();
        if (!configurationList.isEmpty()) {
            Configuration configuration = configurationList.get(0);
            request.getSession().setMaxInactiveInterval(configuration.getSessionTimeoutInSeconds());
            request.getSession().setAttribute("passwordComplexity",configuration.getPasswordComplexity().ordinal());
            request.getSession().setAttribute("passwordLength",configuration.getPasswordLength());
        }

        userSessionInformationService.createUserSessionInformation(request.getSession().getId(), authentication.getName(), request.getRemoteAddr());
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (defaultSavedRequest != null) {
            String targetURL = defaultSavedRequest.getRedirectUrl();
            response.sendRedirect(targetURL);
        } else {
            response.sendRedirect("/");
        }
    }
}