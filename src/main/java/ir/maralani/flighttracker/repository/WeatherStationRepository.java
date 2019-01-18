package ir.maralani.flighttracker.repository;


import ir.maralani.flighttracker.model.WeatherStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherStationRepository extends JpaRepository<WeatherStation, Long> {

    WeatherStation findByStationNo(Integer stationNo);
    WeatherStation findByStationId(String stationId);
}
