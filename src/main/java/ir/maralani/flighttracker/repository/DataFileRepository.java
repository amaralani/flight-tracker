package ir.maralani.flighttracker.repository;

import ir.maralani.flighttracker.model.DataFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataFileRepository extends JpaRepository<DataFile,Long> {
}
