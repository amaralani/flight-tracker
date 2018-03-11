package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.TrackLog;

import java.util.List;

public interface TrackLogService extends BaseService<TrackLog> {
    List<TrackLog> getByCode(String code);
}
