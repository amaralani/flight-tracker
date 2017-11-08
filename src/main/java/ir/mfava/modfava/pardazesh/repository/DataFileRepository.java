package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.DataFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataFileRepository extends JpaRepository<DataFile,Long> {
}
