package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.Bulletin;

import java.util.Date;
import java.util.List;


public interface BulletinService extends BaseService<Bulletin> {


    List<Bulletin> getListByProvinceAndForecastDate(Long provinceId, Date yesterday);

    List<Bulletin> getCurrentForecasts();
}
