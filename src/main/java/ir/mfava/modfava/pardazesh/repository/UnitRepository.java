package ir.mfava.modfava.pardazesh.repository;


import ir.mfava.modfava.pardazesh.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {

}
