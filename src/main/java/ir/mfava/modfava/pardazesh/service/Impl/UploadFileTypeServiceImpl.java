package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.UploadFileType;
import ir.mfava.modfava.pardazesh.repository.UploadFileTypeRepository;
import ir.mfava.modfava.pardazesh.service.UploadFileTypeService;
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
