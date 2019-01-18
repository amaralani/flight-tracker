package ir.maralani.flighttracker.service.report.event;

import ir.maralani.flighttracker.model.report.event.ActionImportance;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.BaseService;

public interface ActionImportanceService extends BaseService<ActionImportance> {
    ActionImportance getActionImportanceByActionType(Event.ActionType actionType);

    void setActionImportance(Event.ActionType actionType, Event.Importance importance);
}
