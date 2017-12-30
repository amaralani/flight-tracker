package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.UploadFile;

import java.util.List;

public interface UploadFileService extends BaseService<UploadFile> {
    List<UploadFile> getAllAvailable();
}
