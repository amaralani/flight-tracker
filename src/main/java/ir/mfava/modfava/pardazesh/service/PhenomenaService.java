package ir.mfava.modfava.pardazesh.service;


import ir.mfava.modfava.pardazesh.model.Phenomena;

public interface PhenomenaService extends BaseService<Phenomena> {

    Phenomena getByAbbreviation(String abbr);
}
