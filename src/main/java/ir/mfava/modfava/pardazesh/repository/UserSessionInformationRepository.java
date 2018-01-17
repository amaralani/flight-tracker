package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.model.UserSessionInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserSessionInformationRepository extends JpaRepository<UserSessionInformation, Long> {

    UserSessionInformation getBySessionId(String sessionId);

    UserSessionInformation getTop1ByUserIdOrderByStartDateDesc(Long userId);

    List<UserSessionInformation> getAllByStartDateGreaterThanAndEndDateLessThanAndUser(Date startDate, Date endDate, User user);
}
