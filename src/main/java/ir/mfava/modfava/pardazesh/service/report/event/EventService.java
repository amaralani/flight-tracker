package ir.mfava.modfava.pardazesh.service.report.event;


import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.BaseService;

import java.util.Map;

public interface EventService extends BaseService<ir.mfava.modfava.pardazesh.model.report.event.Event> {

    void addEvent(String clientIp, String clientName, String url, String username, Event.ActionType actionType, Event.SubType subType, Event.Flag flag, Map<String, String> descriptionElements, Event.Sensitivity sensitivity);
}
