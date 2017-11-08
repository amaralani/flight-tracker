package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Province;
import ir.mfava.modfava.pardazesh.repository.ProvinceRepository;
import ir.mfava.modfava.pardazesh.service.ProvinceService;
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
