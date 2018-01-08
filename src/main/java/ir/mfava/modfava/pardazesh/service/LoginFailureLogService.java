package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.LoginFailureLog;

public interface LoginFailureLogService extends BaseService<LoginFailureLog> {
    void createLog(String username, String remoteAddress);

    Long countUserLogsSinceLastLogin(String username);
}
