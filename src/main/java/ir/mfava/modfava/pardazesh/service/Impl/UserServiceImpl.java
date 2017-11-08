package ir.mfava.modfava.pardazesh.service.Impl;


import ir.mfava.modfava.pardazesh.model.Role;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.repository.RoleRepository;
import ir.mfava.modfava.pardazesh.repository.UserRepository;
import ir.mfava.modfava.pardazesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Transactional(transactionManager = "transactionManager")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean exists(User entity) {
        return userRepository.exists(Example.of(entity));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(User entity) {
        userRepository.delete(entity);
    }

    @Transactional
    @Override
    public void addNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.getOne(2L));
        user.setRoles(roles);
        user.setEnabled(true);
        user.setCreateDate(new Date());
        save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
