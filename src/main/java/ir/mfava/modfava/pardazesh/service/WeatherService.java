package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.DTO.AnalyticDTO;
import ir.mfava.modfava.pardazesh.model.Weather;

import java.util.Date;
import java.util.List;

public interface WeatherService extends BaseService<Weather> {
    List<Weather> getCurrentWeather();

    List<Weather> searchWeather(Weather.ReportType reportType, Date startDate, Date endDate, List<Long> phenomenaIds, Long stationId);

    List<AnalyticDTO> getAnalyticReport(Long stationId, Date startDate, Date endDate);

    List<AnalyticDTO> getTypeCountReport(Long stationId, Date startDate, Date endDate);

    List<AnalyticDTO> getSendCountReport(List<Long> stationIds, Date startDate, Date endDate);

    Boolean process();
}
