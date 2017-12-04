package ir.mfava.modfava.pardazesh.interceptor;

import ir.mfava.modfava.pardazesh.model.Constants;
import ir.mfava.modfava.pardazesh.model.ContentText;
import ir.mfava.modfava.pardazesh.model.Message;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.service.ContentTextService;
import ir.mfava.modfava.pardazesh.service.MessageService;
import ir.mfava.modfava.pardazesh.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ContentTextService contentTextService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        int sessionCount = 0;

        for (final Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> activeUserSessions =
                    sessionRegistry.getAllSessions(principal,/* includeExpiredSessions */ false);
            for (SessionInformation sessionInformation : activeUserSessions) {
                if (!sessionInformation.isExpired() && sessionInformation.getLastRequest().getTime() < (new Date().getTime() - (60 * 5 * 1000))) {
                    sessionInformation.expireNow();
                }
            }
            activeUserSessions = sessionRegistry.getAllSessions(principal,/* includeExpiredSessions */ false);

            if (!activeUserSessions.isEmpty()) {
                sessionCount++;
            }
        }
        session.setAttribute("onlineUserCount", sessionCount);

        if (request.getUserPrincipal() != null) {
            User user = userService.findByUsername(request.getUserPrincipal().getName());
            List<Message> messageList = messageService.getUserMessagesByType(user.getId(), Constants.MessageStatus.UNREAD);
            session.setAttribute("hasUnreadMessage", !messageList.isEmpty());
        }

        ContentText contentText = contentTextService.getByTextContext(ContentText.TextContext.BANNER);
        if (contentText != null && !contentText.getText().isEmpty()) {
            session.setAttribute("bannerText", contentText.getText());
        }
        super.postHandle(request, response, handler, modelAndView);
    }
}
