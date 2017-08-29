package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long>{
}
