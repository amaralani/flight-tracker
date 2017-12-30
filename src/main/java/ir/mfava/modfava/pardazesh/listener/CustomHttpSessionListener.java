package ir.mfava.modfava.pardazesh.listener;

import ir.mfava.modfava.pardazesh.service.UserSessionInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class CustomHttpSessionListener implements HttpSessionListener {

    @Autowired
    private UserSessionInformationService userSessionInformationService;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        userSessionInformationService.expireUserSession(httpSessionEvent.getSession().getId());
    }
}