package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
}
