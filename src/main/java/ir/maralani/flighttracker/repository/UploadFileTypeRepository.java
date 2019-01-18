package ir.maralani.flighttracker.repository;

import ir.maralani.flighttracker.model.UploadFileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileTypeRepository extends JpaRepository<UploadFileType,Long> {

    List<UploadFileType> getAllByActive(Boolean active);
}
