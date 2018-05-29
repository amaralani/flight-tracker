package ir.mfava.modfava.pardazesh.service.Impl.report.event;

import ir.mfava.modfava.pardazesh.model.report.event.SoftwareInfo;
import ir.mfava.modfava.pardazesh.repository.report.event.SoftwareInfoRepository;
import ir.mfava.modfava.pardazesh.service.report.event.SoftwareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoftwareInfoServiceImpl implements SoftwareInfoService {

    @Autowired
    private SoftwareInfoRepository softwareInfoRepository;

    @Override
    public boolean exists(SoftwareInfo entity) {
        return softwareInfoRepository.exists(Example.of(entity));
    }

    @Override
    public List<SoftwareInfo> getAll() {
        return softwareInfoRepository.findAll();
    }

    @Override
    public SoftwareInfo getById(Long id) {
        return softwareInfoRepository.getOne(id);
    }

    @Override
    public SoftwareInfo save(SoftwareInfo entity) {
        return softwareInfoRepository.save(entity);
    }

    @Override
    public void remove(SoftwareInfo entity) {
        softwareInfoRepository.delete(entity);
    }

    @Override
    public SoftwareInfo getByUsername(String username){
        return softwareInfoRepository.getByUserName(username);
    }
}
