package ir.mfava.modfava.pardazesh.service.Impl.report.event;


import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.repository.report.event.EventRepository;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

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
        if (eventRepository.getActionImportanceByActionType(actionType) != null) {
            return eventRepository.getActionImportanceByActionType(actionType).getImportance();
        }
        return null;
    }

    @Override
    public void addEvent(String clientIp, String clientName, String url, String username, Event.ActionType actionType, Event.SubType subType, Event.Flag flag, Map<String, String> descriptionElements, Event.Sensitivity sensitivity) {
        try {
            Event event = new Event();
            event.setClientIp(clientIp);
            event.setClientName(clientName);
            event.setUrl(url);
            event.setUsername(username);
            event.setActionType(actionType);
            event.setSubType(subType);
            event.setFlag(flag);
            // TODO‌: convert descriptionElements to JSON
            event.setDescription("");
            event.setImportance(getEventImportanceByAction(actionType));
            event.setSensitivity(sensitivity);
            save(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
