package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Weather;
import ir.mfava.modfava.pardazesh.repository.WeatherRepository;
import ir.mfava.modfava.pardazesh.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Override
    public boolean exists(Weather entity) {
        return weatherRepository.exists(Example.of(entity));
    }

    @Override
    public List<Weather> getAll() {
        return weatherRepository.findAll();
    }

    @Override
    public Weather getById(String id) {
        return weatherRepository.getOne(Long.valueOf(id));
    }

    @Override
    public Weather save(Weather entity) {
        return weatherRepository.save(entity);
    }

    @Override
    public void remove(Weather entity) {
        weatherRepository.delete(entity);
    }
}

