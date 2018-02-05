package ir.mfava.modfava.pardazesh.service;


import ir.mfava.modfava.pardazesh.model.Role;
import ir.mfava.modfava.pardazesh.model.User;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Drago
 * @since 1.0
 */
public interface UserService extends BaseService<User> {
    void addNewUser(User user);
    User save(User user);

    User findByUsername(String username);

    List<BigInteger> getRoleUsers(Long roleId);
}
