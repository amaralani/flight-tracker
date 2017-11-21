package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.Message;

import java.util.List;


public interface MessageService extends BaseService<Message> {
    void sendMessage(String[] userIds, Message message);

    List<Message> getByExample(Message message);

    List<Message> getUserMessagesByType(Long userId, Boolean type);
}
