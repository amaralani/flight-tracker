package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.UploadFileTypeRepository;
import ir.maralani.flighttracker.model.UploadFileType;
import ir.maralani.flighttracker.repository.UploadFileTypeRepository;
import ir.maralani.flighttracker.service.UploadFileTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFileTypeServiceImpl implements UploadFileTypeService {

    @Autowired
    private UploadFileTypeRepository uploadFileTypeRepository;

    @Override
    public boolean exists(UploadFileType entity) {
        return uploadFileTypeRepository.exists(Example.of(entity));
    }

    @Override
    public List<UploadFileType> getAll() {
        return uploadFileTypeRepository.findAll();
    }

    @Override
    public List<UploadFileType> getAllActive() {
        return uploadFileTypeRepository.getAllByActive(true);
    }


    @Override
    public UploadFileType getById(Long id) {
        return uploadFileTypeRepository.getOne(id);
    }

    @Override
    public UploadFileType save(UploadFileType entity) {
        return uploadFileTypeRepository.save(entity);
    }

    @Override
    public void remove(UploadFileType entity) {
        uploadFileTypeRepository.delete(entity);
    }
}
