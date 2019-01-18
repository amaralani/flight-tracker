package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.LoginFailureLogRepository;
import ir.maralani.flighttracker.repository.UserRepository;
import ir.maralani.flighttracker.repository.UserSessionInformationRepository;
import ir.maralani.flighttracker.model.LoginFailureLog;
import ir.maralani.flighttracker.model.User;
import ir.maralani.flighttracker.model.UserSessionInformation;
import ir.maralani.flighttracker.repository.LoginFailureLogRepository;
import ir.maralani.flighttracker.repository.UserRepository;
import ir.maralani.flighttracker.repository.UserSessionInformationRepository;
import ir.maralani.flighttracker.service.LoginFailureLogService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class LoginFailureLogServiceImpl implements LoginFailureLogService {

    @Autowired
    private LoginFailureLogRepository loginFailureLogRepository;
    @Autowired
    private UserSessionInformationRepository userSessionInformationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean exists(LoginFailureLog entity) {
        return loginFailureLogRepository.exists(Example.of(entity));
    }

    @Override
    public List<LoginFailureLog> getAll() {
        return loginFailureLogRepository.findAll();
    }

    @Override
    public LoginFailureLog getById(Long id) {
        return loginFailureLogRepository.getOne(id);
    }

    @Transactional
    @Override
    public LoginFailureLog save(LoginFailureLog entity) {
        return loginFailureLogRepository.save(entity);
    }

    @Transactional
    @Override
    public void remove(LoginFailureLog entity) {
        loginFailureLogRepository.delete(entity);
    }

    @Transactional
    @Override
    public void createLog(String username, String remoteAddress) {
        LoginFailureLog loginFailureLog = new LoginFailureLog();
        loginFailureLog.setUsername(username);
        loginFailureLog.setIp(remoteAddress);
        loginFailureLog.setDateTime(new Date());
        save(loginFailureLog);
    }

    @Override
    public Long countUserLogsSinceLastLogin(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            UserSessionInformation userSessionInformation = userSessionInformationRepository.getTop1ByUserIdOrderByStartDateDesc(user.getId());
            return userSessionInformation != null ? loginFailureLogRepository.countByDateTimeGreaterThanAndUsername(userSessionInformation.getStartDate(), username) : 0;
        } else {
            return loginFailureLogRepository.countByDateTimeGreaterThanAndUsername(DateUtils.addDays(new Date(), -1), username);
        }
    }

    @Override
    public List<LoginFailureLog> getByDateAndUsername(Date startDate, Date endDate, String username, String ip){
        return loginFailureLogRepository.getAllByDateTimeAndUsernameAndIp(startDate,endDate,username,ip);
    }
}
