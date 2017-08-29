package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Unit;
import ir.mfava.modfava.pardazesh.model.UnitType;
import ir.mfava.modfava.pardazesh.repository.UnitRepository;
import ir.mfava.modfava.pardazesh.repository.UnitTypeRepository;
import ir.mfava.modfava.pardazesh.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private UnitTypeRepository unitTypeRepository;

    @Override
    public boolean exists(Unit entity) {
        return unitRepository.exists(Example.of(entity));
    }

    @Override
    public List<Unit> getAll() {
        return unitRepository.findAll();
    }

    @Override
    public Unit getById(String id) {
        return unitRepository.getOne(Long.valueOf(id));
    }

    @Override
    public Unit save(Unit entity) {
        return unitRepository.save(entity);
    }

    @Override
    public void remove(Unit entity) {
        unitRepository.delete(entity);
    }

    @Override
    public UnitType getUnitTypeById(Long id) {
        return unitTypeRepository.getOne(id);
    }

    @Override
    public UnitType save(UnitType unitType) {
        return unitTypeRepository.save(unitType);
    }

    @Override
    public List<UnitType> getAllUnitTypes() {
        return unitTypeRepository.findAll();
    }
}
