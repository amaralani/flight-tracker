package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.model.UserSessionInformation;
import ir.mfava.modfava.pardazesh.repository.UserSessionInformationRepository;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.service.UserSessionInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
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
}
