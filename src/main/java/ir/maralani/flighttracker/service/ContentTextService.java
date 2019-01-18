package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.ContentText;

public interface ContentTextService extends BaseService<ContentText> {
    ContentText getByTextContext(ContentText.TextContext textContext);
}
