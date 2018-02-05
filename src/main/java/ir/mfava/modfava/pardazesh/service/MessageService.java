package ir.mfava.modfava.pardazesh.service;

import ir.mfava.modfava.pardazesh.model.Message;

import java.util.List;


public interface MessageService extends BaseService<Message> {
    void sendMessage(String[] userIds, Message message, String[] roleIds);

    List<Message> getByExample(Message message);

    List<Message> getUserMessagesByType(Long userId, Boolean type);
}
