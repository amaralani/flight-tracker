package ir.mfava.modfava.pardazesh.repository;


import ir.mfava.modfava.pardazesh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
