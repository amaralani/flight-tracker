package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.ContentText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentTextRepository extends JpaRepository<ContentText,Long>{

    ContentText getByTextContext(ContentText.TextContext textContext);
}
