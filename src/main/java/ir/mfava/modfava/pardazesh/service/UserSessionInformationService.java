package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.UserSessionInformation;


public interface UserSessionInformationService extends BaseService<UserSessionInformation> {
    void expireUserSession(String sessionId);

    void createUserSessionInformation(String sessionId, String username,String remoteAddress);
}
