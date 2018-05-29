package ir.mfava.modfava.pardazesh.service.report.event;

import ir.mfava.modfava.pardazesh.model.report.event.ActionImportance;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.BaseService;

public interface ActionImportanceService extends BaseService<ActionImportance> {
    ActionImportance getActionImportanceByActionType(Event.ActionType actionType);

    void setActionImportance(Event.ActionType actionType, Event.Importance importance);
}
