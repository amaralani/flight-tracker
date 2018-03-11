package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Track;
import ir.mfava.modfava.pardazesh.model.TrackLog;
import ir.mfava.modfava.pardazesh.repository.TrackLogRepository;
import ir.mfava.modfava.pardazesh.service.TrackLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackLogServiceImpl implements TrackLogService {
    @Autowired
    private TrackLogRepository trackLogRepository;

    @Override
    public boolean exists(TrackLog entity) {
        return trackLogRepository.exists(Example.of(entity));
    }

    @Override
    public List<TrackLog> getAll() {
        return trackLogRepository.findAll();
    }

    @Override
    public TrackLog getById(Long id) {
        return trackLogRepository.getOne(id);
    }

    @Override
    public TrackLog save(TrackLog entity) {
        return trackLogRepository.save(entity);
    }

    @Override
    public void remove(TrackLog entity) {
        trackLogRepository.delete(entity);
    }

    @Override
    public List<TrackLog> getByCode(String code) {
        TrackLog track = new TrackLog();
        track.setCode(code);
        return trackLogRepository.findAll(Example.of(track));
    }
}
