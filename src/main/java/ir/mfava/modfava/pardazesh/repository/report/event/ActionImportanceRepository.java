package ir.mfava.modfava.pardazesh.repository.report.event;

import ir.mfava.modfava.pardazesh.model.report.event.ActionImportance;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionImportanceRepository extends JpaRepository<ActionImportance, Long> {

    @Query("from ActionImportance where actionType = :actionType ")
    ActionImportance getActionImportanceByActionType(@Param("actionType") Event.ActionType actionType);

}
