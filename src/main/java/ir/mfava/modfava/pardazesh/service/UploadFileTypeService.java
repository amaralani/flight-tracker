package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.UploadFileType;

import java.util.List;

public interface UploadFileTypeService extends BaseService<UploadFileType> {
    List<UploadFileType> getAllActive();
}
