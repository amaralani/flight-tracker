package ir.maralani.flighttracker.service;


import ir.maralani.flighttracker.model.WeatherStation;

public interface WeatherStationService extends BaseService<WeatherStation> {
    WeatherStation getByStationNo(Integer stationNo);

    WeatherStation getByStationId(String stationId);
}
