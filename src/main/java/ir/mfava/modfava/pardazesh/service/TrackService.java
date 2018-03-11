package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.Track;

import java.util.List;

public interface TrackService extends BaseService<Track> {
    List<Track> findByExample(Track track);
}
