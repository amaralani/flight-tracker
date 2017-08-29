package ir.mfava.modfava.pardazesh.repository;


import ir.mfava.modfava.pardazesh.model.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {

}
