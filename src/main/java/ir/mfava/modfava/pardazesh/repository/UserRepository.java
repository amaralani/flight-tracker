package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Role;
import ir.mfava.modfava.pardazesh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author Drago
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByUserGroupId(Long userGroupId);
}
