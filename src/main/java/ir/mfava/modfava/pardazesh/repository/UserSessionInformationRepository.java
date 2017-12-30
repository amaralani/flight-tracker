package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.UserSessionInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionInformationRepository extends JpaRepository<UserSessionInformation, Long> {

    UserSessionInformation getBySessionId(String sessionId);
}
