package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.LoginFailureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface LoginFailureLogRepository extends JpaRepository<LoginFailureLog, Long> {

    Long countByDateTimeGreaterThanAndUsername(Date dateTime, String username);
}
