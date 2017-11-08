package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Phenomena;
import ir.mfava.modfava.pardazesh.repository.PhenomenaRepository;
import ir.mfava.modfava.pardazesh.service.PhenomenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhenomenaServiceImpl implements PhenomenaService {
    @Autowired
    private PhenomenaRepository phenomenaRepository;

    @Override
    public boolean exists(Phenomena entity) {
        return phenomenaRepository.exists(Example.of(entity));
    }

    @Override
    public List<Phenomena> getAll() {
        return phenomenaRepository.findAll();
    }

    @Override
    public Phenomena getById(Long id) {
        return phenomenaRepository.getOne(id);
    }

    @Override
    public Phenomena save(Phenomena entity) {
        return phenomenaRepository.save(entity);
    }

    @Override
    public void remove(Phenomena entity) {
        phenomenaRepository.delete(entity);
    }

    @Override
    public Phenomena getByAbbreviation(String abbr){
        return phenomenaRepository.getFirstByAbbreviation(abbr);
    }
}
