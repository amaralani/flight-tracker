package ir.maralani.flighttracker.repository;

import ir.maralani.flighttracker.model.TrackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackLogRepository extends JpaRepository<TrackLog, Long> {

    @Query(value = "SELECT * " +
            "FROM tracklog tl " +
            "WHERE tracklog_id = ( " +
            "  SELECT tracklog_id " +
            "  FROM tracklog tli " +
            "  WHERE tli.tracklog_code = tl.tracklog_code " +
            "  LIMIT 1) " +
            " and tracklog_col3 = :aircraft order by tracklog_time desc " +
            " LIMIT 10 ",nativeQuery = true)
    List<TrackLog> getTrackHistoryByAircraft(@Param("aircraft") String aircraft);

    TrackLog getTop1ByCode(String code);

    TrackLog getTop1ByCol3OrderByTimeDesc(String col3);
}
