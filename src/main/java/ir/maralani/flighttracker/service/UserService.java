package ir.maralani.flighttracker.service;


import ir.maralani.flighttracker.model.User;

import java.util.List;

/**
 * @author Drago
 * @since 1.0
 */
public interface UserService extends BaseService<User> {
    void addNewUser(User user);

    User save(User user);

    User findByUsername(String username);

    List<User> getUsersByGroupId(Long userGroupId);
}
