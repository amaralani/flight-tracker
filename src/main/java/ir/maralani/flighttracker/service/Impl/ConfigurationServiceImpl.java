package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.ConfigurationRepository;
import ir.maralani.flighttracker.model.Configuration;
import ir.maralani.flighttracker.repository.ConfigurationRepository;
import ir.maralani.flighttracker.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public boolean exists(Configuration entity) {
        return configurationRepository.exists(Example.of(entity));
    }

    @Override
    public List<Configuration> getAll() {
        return configurationRepository.findAll();
    }

    @Override
    public Configuration getById(Long id) {
        return configurationRepository.getOne(id);
    }

    @Override
    public Configuration save(Configuration entity) {
        return configurationRepository.save(entity);
    }

    @Override
    public void remove(Configuration entity) {
        configurationRepository.delete(entity);
    }
}
