package ir.mfava.modfava.pardazesh.service;


import ir.mfava.modfava.pardazesh.model.WeatherStation;

public interface WeatherStationService extends BaseService<WeatherStation> {
    WeatherStation getByStationNo(Integer stationNo);

}
