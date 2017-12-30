package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.LoginFailureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginFailureLogRepository extends JpaRepository<LoginFailureLog, Long> {
}
