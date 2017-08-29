package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.WeatherStation;
import ir.mfava.modfava.pardazesh.repository.WeatherStationRepository;
import ir.mfava.modfava.pardazesh.service.WeatherStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherStationServiceImpl implements WeatherStationService {

    @Autowired
    private WeatherStationRepository weatherStationRepository;

    @Override
    public boolean exists(WeatherStation entity) {
        return false;
    }

    @Override
    public List<WeatherStation> getAll() {
        return weatherStationRepository.findAll();
    }

    @Override
    public WeatherStation getById(String id) {
        return weatherStationRepository.getOne(Long.valueOf(id));
    }

    @Override
    public WeatherStation getByStationNo(Integer stationNo) {
        return weatherStationRepository.findByStationNo(stationNo);
    }

    @Override
    public WeatherStation save(WeatherStation entity) {
        return weatherStationRepository.save(entity);
    }

    @Override
    public void remove(WeatherStation entity) {
        weatherStationRepository.delete(entity.getId());
    }
}
