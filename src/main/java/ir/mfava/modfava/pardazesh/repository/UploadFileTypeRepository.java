package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.UploadFileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileTypeRepository extends JpaRepository<UploadFileType,Long> {

    List<UploadFileType> getAllByActive(Boolean active);
}
