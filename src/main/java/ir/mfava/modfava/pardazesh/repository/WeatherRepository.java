package ir.mfava.modfava.pardazesh.repository;


import ir.mfava.modfava.pardazesh.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query(nativeQuery = true, value = "select wo.* from ( " +
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

    @Query(value = " from Weather w " +
            " where (:reportType is null or reportType = :reportType ) " +
            " and (:stationId is null or weatherStation.id = :stationId ) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) ")
    List<Weather> searchWeather(@Param("reportType") Weather.ReportType reportType, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("stationId") Long stationId);

    @Query(value = " from Weather w " +
            " where (:reportType is null or reportType = :reportType ) " +
            " and (:stationId is null or weatherStation.id = :stationId ) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) " +
            " and (phenomena.id in :phenomenaIds) ")
    List<Weather> searchWeather(@Param("reportType") Weather.ReportType reportType, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("phenomenaIds") List<Long> phenomenaIds, @Param("stationId") Long stationId);

    @Query(value = "select w.phenomena.abbreviation as phenomenaName, count(w.id) as cunt from Weather w " +
            " where (:stationId is null or weatherStation.id = :stationId ) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) " +
            " group by w.phenomena.abbreviation ")
    List<Object> getAnalyticData(@Param("stationId") Long stationId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select  count(w.id) as cunt from Weather w " +
            " where (:stationId is null or weatherStation.id = :stationId ) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) ")
    Integer getTotalDataCount(@Param("stationId") Long stationId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select w.reportType , count(w.id)  from Weather w " +
            " where (:stationId is null or weatherStation.id = :stationId ) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) " +
            " group by w.reportType ")
    List<Object> getTypeCountData(@Param("stationId") Long stationId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select w.weatherStation.name , count(w.id)  from Weather w " +
            " where (weatherStation.id in :stationIds) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) " +
            " group by w.weatherStation.name ")
    List<Object> getSendCountData(@Param("stationIds") List<Long> stationIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select w.weatherStation.name , count(w.id)  from Weather w " +
            " where (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) " +
            " group by w.weatherStation.name ")
    List<Object> getSendCountData(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(w.id)  from Weather w " +
            " where (weatherStation.id in :stationIds) " +
            " and (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) ")
    Integer getCountSendCountData(@Param("stationIds") List<Long> stationIds, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = "select count(w.id)  from Weather w " +
            " where (reportDate >= :startDate) " +
            " and (reportDate <= :endDate) ")
    Integer getCountSendCountData(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
