package ir.maralani.flighttracker.repository;

import ir.maralani.flighttracker.model.User;
import ir.maralani.flighttracker.model.UserSessionInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserSessionInformationRepository extends JpaRepository<UserSessionInformation, Long> {

    UserSessionInformation getBySessionId(String sessionId);

    UserSessionInformation getTop1ByUserIdOrderByStartDateDesc(Long userId);

    List<UserSessionInformation> getAllByStartDateGreaterThanAndEndDateLessThanAndUser(Date startDate, Date endDate, User user);

    @Query(value = "from UserSessionInformation where  endDate is null and startDate >= :day ")
    List<UserSessionInformation> getOnlineUsers(@Param("day") Date day);

    List<UserSessionInformation> getTop10ByUserOrderByStartDateDesc(User user);
}
