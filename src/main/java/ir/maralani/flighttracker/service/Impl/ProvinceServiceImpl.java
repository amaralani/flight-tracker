package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.ProvinceRepository;
import ir.maralani.flighttracker.model.Province;
import ir.maralani.flighttracker.repository.ProvinceRepository;
import ir.maralani.flighttracker.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;
    @Override
    public boolean exists(Province entity) {
        return provinceRepository.exists(Example.of(entity));
    }

    @Override
    public List<Province> getAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Province getById(Long id) {
        return provinceRepository.getOne(id);
    }

    @Override
    public Province save(Province entity) {
        return provinceRepository.save(entity);
    }

    @Override
    public void remove(Province entity) {
        provinceRepository.delete(entity);
    }
}
