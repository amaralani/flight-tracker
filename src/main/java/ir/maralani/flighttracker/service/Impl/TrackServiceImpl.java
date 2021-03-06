package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.TrackRepository;
import ir.maralani.flighttracker.model.Track;
import ir.maralani.flighttracker.repository.TrackRepository;
import ir.maralani.flighttracker.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImpl implements TrackService {
    @Autowired
    private TrackRepository trackRepository;

    @Override
    public boolean exists(Track entity) {
        return trackRepository.exists(Example.of(entity));
    }

    @Override
    public List<Track> getAll() {
        return trackRepository.findAll();
    }

    @Override
    public Track getById(Long id) {
        return trackRepository.getOne(id);
    }

    @Override
    public Track save(Track entity) {
        return trackRepository.save(entity);
    }

    @Override
    public void remove(Track entity) {
        trackRepository.delete(entity);
    }

    @Override
    public List<Track> findByExample(Track track) {
        return trackRepository.findTracks(track.getCode(), track.getSpeed(), track.getRadar(), track.getLongitude(), track.getLatitude(), track.getAltitude(), track.getSource(), track.getDestination(), track.getCol3());
    }
}
