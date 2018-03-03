package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.model.UserSessionInformation;

import java.util.Date;
import java.util.List;


public interface UserSessionInformationService extends BaseService<UserSessionInformation> {
    void expireUserSession(String sessionId);

    void createUserSessionInformation(String sessionId, String username, String remoteAddress);

    List<UserSessionInformation> searchSessionInformations(Long userId, Date startDate, Date endDate);

    List<UserSessionInformation> getTop10LastUserSessions(User user);

    List<UserSessionInformation> getOnlineUsers();
}
