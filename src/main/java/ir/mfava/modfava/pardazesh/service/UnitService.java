package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.Unit;
import ir.mfava.modfava.pardazesh.model.UnitType;

import java.util.List;


public interface UnitService extends BaseService<Unit> {
    UnitType getUnitTypeById(Long id);

    UnitType save(UnitType unitType);

    List<UnitType> getAllUnitTypes();
}
