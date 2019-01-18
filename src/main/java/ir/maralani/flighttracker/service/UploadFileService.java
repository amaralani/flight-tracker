package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.UploadFile;

import java.util.List;

public interface UploadFileService extends BaseService<UploadFile> {
    List<UploadFile> getAllAvailable();
}
