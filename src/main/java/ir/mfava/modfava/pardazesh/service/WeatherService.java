package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.Weather;

import java.util.List;

public interface WeatherService extends BaseService<Weather> {
    List<Weather> getCurrentWeather();

    Boolean process();
}
