package ir.mfava.modfava.pardazesh.service.report.event;

import ir.mfava.modfava.pardazesh.model.report.event.SoftwareInfo;
import ir.mfava.modfava.pardazesh.service.BaseService;

public interface SoftwareInfoService extends BaseService<SoftwareInfo> {
    SoftwareInfo getByUsername(String username);
}
