package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.TrackLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackLogRepository extends JpaRepository<TrackLog, Long> {
}