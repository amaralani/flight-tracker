package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.Track;

import java.util.List;

public interface TrackService extends BaseService<Track> {
    List<Track> findByExample(Track track);
}
