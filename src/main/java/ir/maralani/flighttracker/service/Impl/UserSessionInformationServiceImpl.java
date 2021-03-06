package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.UserSessionInformationRepository;
import ir.maralani.flighttracker.model.User;
import ir.maralani.flighttracker.model.UserSessionInformation;
import ir.maralani.flighttracker.repository.UserSessionInformationRepository;
import ir.maralani.flighttracker.service.UserService;
import ir.maralani.flighttracker.service.UserSessionInformationService;
import ir.maralani.flighttracker.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserSessionInformationServiceImpl implements UserSessionInformationService {

    @Autowired
    private UserSessionInformationRepository userSessionInformationRepository;
    @Autowired
    private UserService userService;

    @Override
    public boolean exists(UserSessionInformation entity) {
        return userSessionInformationRepository.exists(Example.of(entity));
    }

    @Override
    public List<UserSessionInformation> getAll() {
        return userSessionInformationRepository.findAll();
    }

    @Override
    public UserSessionInformation getById(Long id) {
        return userSessionInformationRepository.getOne(id);
    }

    @Transactional
    @Override
    public UserSessionInformation save(UserSessionInformation entity) {
        return userSessionInformationRepository.save(entity);
    }

    @Transactional
    @Override
    public void remove(UserSessionInformation entity) {
        userSessionInformationRepository.delete(entity);
    }

    @Transactional
    @Override
    public void expireUserSession(String sessionId) {
        UserSessionInformation userSessionInformation = userSessionInformationRepository.getBySessionId(sessionId);
        if (userSessionInformation != null && userSessionInformation.getEndDate() == null) {
            userSessionInformation.setEndDate(new Date());
            userSessionInformationRepository.save(userSessionInformation);
        }
    }

    @Transactional
    @Override
    public void createUserSessionInformation(String sessionId, String username,String remoteAddress) {
        UserSessionInformation userSessionInformation = new UserSessionInformation();
        User user = userService.findByUsername(username);
        if (user != null) {
            userSessionInformation.setUser(user);
            userSessionInformation.setIp(remoteAddress);
            userSessionInformation.setStartDate(new Date());
            userSessionInformation.setEndDate(null);
            userSessionInformation.setSessionId(sessionId);
            save(userSessionInformation);
        }
    }

    @Override
    public List<UserSessionInformation> searchSessionInformations(Long userId, Date startDate, Date endDate){
        return userSessionInformationRepository.getAllByStartDateGreaterThanAndEndDateLessThanAndUser(startDate,endDate,userService.getById(userId));
    }

    @Override
    public List<UserSessionInformation> getTop10LastUserSessions(User user){
        return userSessionInformationRepository.getTop10ByUserOrderByStartDateDesc(user);
    }

    @Override
    public List<UserSessionInformation> getOnlineUsers(){
        return userSessionInformationRepository.getOnlineUsers(DateUtils.clearTime(new Date()));
    }
}
