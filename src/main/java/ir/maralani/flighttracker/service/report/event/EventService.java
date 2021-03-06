package ir.maralani.flighttracker.service.report.event;


import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.service.BaseService;

import java.util.List;
import java.util.Map;

public interface EventService extends BaseService<Event> {

    void addEvent(String clientIp, String clientName, String hostIp, String hostName, String url, String username, Event.ActionType actionType, Event.SubType subType, Event.Flag flag, Map<String, String> descriptionElements, Event.Sensitivity sensitivity);

    List<Event> search(Long fromDate, Long toDate, Event.ActionType actionType, Event.SubType subType, Event.Sensitivity sensitivity, Event.Importance importance);
}
