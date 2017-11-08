package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Defactor;
import ir.mfava.modfava.pardazesh.repository.DefactorRepository;
import ir.mfava.modfava.pardazesh.service.DefactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefactorServiceImpl implements DefactorService {
    @Autowired
    private DefactorRepository defactorRepository;

    @Override
    public boolean exists(Defactor entity) {
        return defactorRepository.exists(Example.of(entity));
    }

    @Override
    public List<Defactor> getAll() {
        return defactorRepository.findAll();
    }

    @Override
    public Defactor getById(Long id) {
        return defactorRepository.getOne(id);
    }

    @Override
    public Defactor save(Defactor entity) {
        return defactorRepository.save(entity);
    }

    @Override
    public void remove(Defactor entity) {
        defactorRepository.delete(entity);
    }
}
