package ir.mfava.modfava.pardazesh.service.Impl.report.event;


import ir.mfava.modfava.pardazesh.model.report.event.ActionImportance;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.repository.report.event.EventRepository;
import ir.mfava.modfava.pardazesh.service.report.event.ActionImportanceService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import ir.mfava.modfava.pardazesh.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ActionImportanceService actionImportanceService;

    @Override
    public boolean exists(Event entity) {
        return eventRepository.exists(Example.of(entity));
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event getById(Long id) {
        return eventRepository.getOne(id);
    }

    @Override
    public Event save(Event entity) {
        return eventRepository.save(entity);
    }

    @Override
    public void remove(Event entity) {
        eventRepository.delete(entity);
    }

    private Event.Importance getEventImportanceByAction(Event.ActionType actionType) {
        ActionImportance actionImportance = actionImportanceService.getActionImportanceByActionType(actionType);
        return actionImportance != null ? actionImportance.getImportance() : null;
    }

    @Override
    public void addEvent(String clientIp, String clientName, String hostIp, String hostName, String url, String username, Event.ActionType actionType, Event.SubType subType, Event.Flag flag, Map<String, String> descriptionElements, Event.Sensitivity sensitivity) {
        try {
            Event event = new Event();
            event.setClientIp(clientIp);
            event.setClientName(clientName);
            event.setHostIp(hostIp);
            event.setHostName(hostName);
            event.setUrl(url);
            event.setUsername(username);
            event.setActionType(actionType);
            event.setSubType(subType);
            event.setFlag(flag);
            event.setDescription(Util.mapToString(descriptionElements));
            event.setImportance(getEventImportanceByAction(actionType));
            event.setSensitivity(sensitivity);
            event.setTime(new Date().getTime());
            save(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Event> search(Long fromDate, Long toDate, Event.ActionType actionType, Event.SubType subType, Event.Sensitivity sensitivity, Event.Importance importance) {
        return eventRepository.searchEvents(fromDate, toDate, actionType, subType, sensitivity, importance);
    }
}
