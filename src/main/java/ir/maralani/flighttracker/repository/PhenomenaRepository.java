package ir.maralani.flighttracker.repository;


import ir.maralani.flighttracker.model.Phenomena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhenomenaRepository extends JpaRepository<Phenomena, Long> {


    Phenomena getFirstByAbbreviation(String abbreviation);
}
