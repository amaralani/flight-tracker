package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.UserGroup;
import ir.mfava.modfava.pardazesh.repository.UserGroupRepository;
import ir.mfava.modfava.pardazesh.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Override
    public boolean exists(UserGroup entity) {
        return userGroupRepository.exists(Example.of(entity));
    }

    @Override
    public List<UserGroup> getAll() {
        return userGroupRepository.findAll();
    }

    @Override
    public UserGroup getById(Long id) {
        return userGroupRepository.getOne(id);
    }

    @Override
    public UserGroup save(UserGroup entity) {
        return userGroupRepository.save(entity);
    }

    @Override
    public void remove(UserGroup entity) {
        userGroupRepository.delete(entity);
    }
}
