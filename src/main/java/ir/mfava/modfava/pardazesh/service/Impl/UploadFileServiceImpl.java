package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.UploadFile;
import ir.mfava.modfava.pardazesh.repository.UploadFileRepository;
import ir.mfava.modfava.pardazesh.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Override
    public boolean exists(UploadFile entity) {
        return uploadFileRepository.exists(Example.of(entity));
    }

    @Override
    public List<UploadFile> getAll() {
        return uploadFileRepository.findAll();
    }

    @Override
    public List<UploadFile> getAllAvailable() {
        return uploadFileRepository.getAllByDeleted(false);
    }

    @Override
    public UploadFile getById(Long id) {
        return uploadFileRepository.getOne(id);
    }

    @Override
    public UploadFile save(UploadFile entity) {
        return uploadFileRepository.save(entity);
    }

    @Override
    public void remove(UploadFile entity) {
        uploadFileRepository.delete(entity);
    }
}
