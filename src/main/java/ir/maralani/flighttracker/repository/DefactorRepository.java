package ir.maralani.flighttracker.repository;

import ir.maralani.flighttracker.model.Defactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefactorRepository extends JpaRepository<Defactor, Long> {
}
