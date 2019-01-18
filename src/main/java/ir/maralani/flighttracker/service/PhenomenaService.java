package ir.maralani.flighttracker.service;


import ir.maralani.flighttracker.model.Phenomena;

public interface PhenomenaService extends BaseService<Phenomena> {

    Phenomena getByAbbreviation(String abbr);
}
