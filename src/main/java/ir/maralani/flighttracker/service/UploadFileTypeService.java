package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.UploadFileType;

import java.util.List;

public interface UploadFileTypeService extends BaseService<UploadFileType> {
    List<UploadFileType> getAllActive();
}
