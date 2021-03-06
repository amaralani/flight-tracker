package ir.maralani.flighttracker.repository;

import ir.maralani.flighttracker.model.Bulletin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BulletinRepository extends JpaRepository<Bulletin, Long> {

    @Query("from Bulletin where (province.id = :provinceId or :provinceId is null) and forecastDate >=:today order by forecastDate asc")
    List<Bulletin> getBulletinsByProvinceAndForecastDate(@Param(value = "provinceId") Long provinceId,
                                                         @Param(value = "today") Date today);

    List<Bulletin> getByForecastDate(@Param(value = "forecastDate") Date forecastDate);
}
