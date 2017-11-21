package ir.mfava.modfava.pardazesh.repository;

import ir.mfava.modfava.pardazesh.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("from Message m where m.receiver.id = :receiverId and (m.read= :type or :type is null) and m.deleted = false ")
    List<Message> getByReceiverIdAndType(@Param(value = "receiverId") Long receiverId,
                                         @Param(value = "type") Boolean type);
}
