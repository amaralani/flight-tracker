package ir.mfava.modfava.pardazesh.service;


import ir.mfava.modfava.pardazesh.model.User;

/**
 * @author Drago
 * @since 1.0
 */
public interface UserService extends BaseService<User> {
    void addNewUser(User user);
    User save(User user);

    User findByUsername(String username);
}
