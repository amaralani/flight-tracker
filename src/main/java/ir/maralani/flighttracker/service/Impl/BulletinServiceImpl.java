package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.BulletinRepository;
import ir.maralani.flighttracker.model.Bulletin;
import ir.maralani.flighttracker.repository.BulletinRepository;
import ir.maralani.flighttracker.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BulletinServiceImpl implements BulletinService {
    @Autowired
    private BulletinRepository bulletinRepository;
    @Override
    public boolean exists(Bulletin entity) {
        return bulletinRepository.exists(Example.of(entity));
    }

    @Override
    public List<Bulletin> getAll() {
        return bulletinRepository.findAll();
    }

    @Override
    public Bulletin getById(Long id) {
        return bulletinRepository.getOne(id);
    }

    @Override
    public Bulletin save(Bulletin entity) {
        return bulletinRepository.save(entity);
    }

    @Override
    public void remove(Bulletin entity) {
        bulletinRepository.delete(entity);
    }

    @Override
    public List<Bulletin> getListByProvinceAndForecastDate(Long provinceId, Date yesterday){
        return bulletinRepository.getBulletinsByProvinceAndForecastDate(provinceId,yesterday);
    }

    @Override
    public List<Bulletin> getCurrentForecasts(){
        return bulletinRepository.getByForecastDate(new Date());
    }
}
