package ir.mfava.modfava.pardazesh.repository.report.event;

import ir.mfava.modfava.pardazesh.model.report.event.SoftwareInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareInfoRepository extends JpaRepository<SoftwareInfo, Long> {

    SoftwareInfo getByUserName(String username);
}
