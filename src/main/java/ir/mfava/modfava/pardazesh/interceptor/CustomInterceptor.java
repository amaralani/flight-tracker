package ir.mfava.modfava.pardazesh.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Component
public class CustomInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        int sessionCount = 0;
        for(final Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> activeUserSessions =
                    sessionRegistry.getAllSessions(principal,/* includeExpiredSessions */ false);
            if (!activeUserSessions.isEmpty()) {
                sessionCount++;
            }

            // TODO : Session timeout is not handled!
        }
        session.setAttribute("onlineUserCount",sessionCount);
        super.postHandle(request, response, handler, modelAndView);
    }
}
