package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.LoginFailureLog;
import ir.mfava.modfava.pardazesh.repository.LoginFailureLogRepository;
import ir.mfava.modfava.pardazesh.service.LoginFailureLogService;
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
    public void createLog(String username, String remoteAddress){
        LoginFailureLog loginFailureLog = new LoginFailureLog();
        loginFailureLog.setUsername(username);
        loginFailureLog.setIp(remoteAddress);
        loginFailureLog.setDateTime(new Date());
        save(loginFailureLog);
    }
}
