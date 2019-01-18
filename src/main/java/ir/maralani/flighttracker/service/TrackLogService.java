package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.TrackLog;

import java.util.List;

public interface TrackLogService extends BaseService<TrackLog> {
    List<TrackLog> getByCode(String code);

    List<TrackLog> getAircraftHistoryByCode(String code);

    TrackLog getAircraftLastPosition(String callsign);
}
