package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
