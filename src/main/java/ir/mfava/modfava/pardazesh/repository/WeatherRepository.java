package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long>{

    @Query(nativeQuery = true,value = "select wo.* from ( " +
            "  SELECT " +
            "    ws.id, " +
            "    (SELECT id " +
            "     FROM weather w " +
            "     WHERE w.weather_station_id = ws.id " +
            "     AND report_date is not null " +
            "     ORDER BY report_date DESC " +
            "     LIMIT 1) weatherId " +
            "  FROM weather_station ws " +
            ") as t inner join weather wo on wo.id = t.weatherId " +
            "where t.weatherId is not null and wo.report_Type = 0 ")
    List<Weather> getCurrentWeather();
}
