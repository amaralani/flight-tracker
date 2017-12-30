package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

    List<UploadFile> getAllByDeleted(Boolean deleted);
}
