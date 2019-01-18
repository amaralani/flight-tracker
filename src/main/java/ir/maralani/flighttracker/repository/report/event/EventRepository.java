package ir.maralani.flighttracker.repository.report.event;

import ir.maralani.flighttracker.model.report.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("from Event e " +
            " where (:fromDate is null or :fromDate <= e.time) " +
            " and (:toDate is null or :toDate >= e.time) " +
            " and (:actionType is null or :actionType = e.actionType) " +
            " and (:subType is null or :subType = e.subType) " +
            " and (:sensitivity is null or :sensitivity = e.sensitivity) " +
            " and (:importance is null or :importance = e.importance) " +
            " order by e.time asc ")
    List<Event> searchEvents(@Param("fromDate") Long fromDate,
                             @Param("toDate") Long toDate,
                             @Param("actionType") Event.ActionType actionType,
                             @Param("subType") Event.SubType subType,
                             @Param("sensitivity") Event.Sensitivity sensitivity,
                             @Param("importance") Event.Importance importance);
}
