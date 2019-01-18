package ir.maralani.flighttracker.service.Impl;

import ir.maralani.flighttracker.repository.ContentTextRepository;
import ir.maralani.flighttracker.model.ContentText;
import ir.maralani.flighttracker.repository.ContentTextRepository;
import ir.maralani.flighttracker.service.ContentTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentTextServiceImpl implements ContentTextService {

    @Autowired
    private ContentTextRepository contentTextRepository;

    @Override
    public boolean exists(ContentText entity) {
        return contentTextRepository.exists(Example.of(entity));
    }

    @Override
    public List<ContentText> getAll() {
        return contentTextRepository.findAll();
    }

    @Override
    public ContentText getById(Long id) {
        return contentTextRepository.getOne(id);
    }

    @Override
    public ContentText save(ContentText entity) {
        return contentTextRepository.save(entity);
    }

    @Override
    public void remove(ContentText entity) {
        contentTextRepository.delete(entity);
    }

    @Override
    public ContentText getByTextContext(ContentText.TextContext textContext) {
        return contentTextRepository.getByTextContext(textContext);
    }
}
