package ir.maralani.flighttracker.service.Impl.report.event;

import ir.maralani.flighttracker.repository.report.event.ActionImportanceRepository;
import ir.maralani.flighttracker.service.report.event.ActionImportanceService;
import ir.maralani.flighttracker.model.report.event.ActionImportance;
import ir.maralani.flighttracker.model.report.event.Event;
import ir.maralani.flighttracker.repository.report.event.ActionImportanceRepository;
import ir.maralani.flighttracker.service.report.event.ActionImportanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionImportanceServiceImpl implements ActionImportanceService {
    @Autowired
    private ActionImportanceRepository actionImportanceRepository;


    @Override
    public boolean exists(ActionImportance entity) {
        return actionImportanceRepository.exists(Example.of(entity));
    }

    @Override
    public List<ActionImportance> getAll() {
        return actionImportanceRepository.findAll();
    }

    @Override
    public ActionImportance getById(Long id) {
        return actionImportanceRepository.getOne(id);
    }

    @Override
    public ActionImportance save(ActionImportance entity) {
        return actionImportanceRepository.save(entity);
    }

    @Override
    public void remove(ActionImportance entity) {
        actionImportanceRepository.delete(entity);
    }

    @Override
    public ActionImportance getActionImportanceByActionType(Event.ActionType actionType) {
        return actionImportanceRepository.getActionImportanceByActionType(actionType);
    }

    @Override
    public void setActionImportance(Event.ActionType actionType, Event.Importance importance) {
        ActionImportance actionImportance = getActionImportanceByActionType(actionType);
        if (actionImportance != null) {
            actionImportance.setImportance(importance);
        } else {
            actionImportance = new ActionImportance();
            actionImportance.setActionType(actionType);
            actionImportance.setImportance(importance);
        }
        save(actionImportance);
    }
}
