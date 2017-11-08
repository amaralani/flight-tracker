package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Defactor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefactorRepository extends JpaRepository<Defactor, Long> {
}
