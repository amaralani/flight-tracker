package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.DataFile;
import ir.mfava.modfava.pardazesh.repository.DataFileRepository;
import ir.mfava.modfava.pardazesh.service.DataFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataFileServiceImpl implements DataFileService {

    @Autowired
    private DataFileRepository dataFileRepository;

    @Override
    public boolean exists(DataFile entity) {
        return dataFileRepository.exists(Example.of(entity));
    }

    @Override
    public List<DataFile> getAll() {
        return dataFileRepository.findAll();
    }

    @Override
    public DataFile getById(Long id) {
        return dataFileRepository.getOne(id);
    }

    @Override
    public DataFile save(DataFile entity) {
        return dataFileRepository.save(entity);
    }

    @Override
    public void remove(DataFile entity) {
        dataFileRepository.delete(entity);
    }
}
