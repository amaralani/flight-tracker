package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.WeatherStationRepository;
import ir.maralani.flighttracker.model.WeatherStation;
import ir.maralani.flighttracker.repository.WeatherStationRepository;
import ir.maralani.flighttracker.service.WeatherStationService;
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
    public WeatherStation getById(Long id) {
        return weatherStationRepository.getOne(id);
    }

    @Override
    public WeatherStation getByStationNo(Integer stationNo) {
        return weatherStationRepository.findByStationNo(stationNo);
    }

    @Override
    public WeatherStation getByStationId(String stationId) {
        return weatherStationRepository.findByStationId(stationId);
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
