package ir.mfava.modfava.pardazesh.repository;


import ir.mfava.modfava.pardazesh.model.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStation, Long> {

    WeatherStation findByStationNo(Integer stationNo);
    WeatherStation findByStationId(String stationId);
}
