package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track,Long> {

    @Query(value = "from Track where (code = :code or :code is null) " +
            " and (speed = :speed or :speed is null) " +
            " and (radar = :radar or :radar is null) " +
            " and (longitude = :longitude or :longitude is null) " +
            " and (latitude = :latitude or :latitude is null) " +
            " and (altitude = :altitude or :altitude is null) " +
            " and (source = :source or :source is null) " +
            " and (destination = :destination or :destination is null) " +
            "  ")
    List<Track> findTracks(@Param("code") String code,
                           @Param("speed") Integer speed,
                           @Param("radar") String radar,
                           @Param("longitude") Float longitude,
                           @Param("latitude") Float latitude,
                           @Param("altitude") Float altitude,
                           @Param("source") String source,
                           @Param("destination") String destination);
}
