package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.LoginFailureLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoginFailureLogRepository extends JpaRepository<LoginFailureLog, Long> {

    Long countByDateTimeGreaterThanAndUsername(Date dateTime, String username);

    @Query(value = "from LoginFailureLog where dateTime >= :startDate and dateTime <= :endDate and (:username is null or username = :username) and (:ip is null or ip = :ip)")
    List<LoginFailureLog> getAllByDateTimeAndUsernameAndIp(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("username") String username,@Param("ip") String ip);
}
