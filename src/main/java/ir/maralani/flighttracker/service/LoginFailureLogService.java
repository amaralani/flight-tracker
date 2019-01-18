package ir.maralani.flighttracker.service;


import ir.maralani.flighttracker.model.LoginFailureLog;

import java.util.Date;
import java.util.List;

public interface LoginFailureLogService extends BaseService<LoginFailureLog> {
    void createLog(String username, String remoteAddress);

    Long countUserLogsSinceLastLogin(String username);

    List<LoginFailureLog> getByDateAndUsername(Date startDate, Date endDate, String username, String ip);
}
