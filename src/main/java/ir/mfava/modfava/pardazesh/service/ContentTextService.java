package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.ContentText;

public interface ContentTextService extends BaseService<ContentText> {
    ContentText getByTextContext(ContentText.TextContext textContext);
}
