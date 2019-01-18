package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.Bulletin;

import java.util.Date;
import java.util.List;


public interface BulletinService extends BaseService<Bulletin> {


    List<Bulletin> getListByProvinceAndForecastDate(Long provinceId, Date yesterday);

    List<Bulletin> getCurrentForecasts();
}
