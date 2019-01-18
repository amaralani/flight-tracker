package ir.maralani.flighttracker.service.report.event;

import ir.maralani.flighttracker.model.report.event.SoftwareInfo;
import ir.maralani.flighttracker.service.BaseService;

public interface SoftwareInfoService extends BaseService<SoftwareInfo> {
    SoftwareInfo getByUsername(String username);
}
