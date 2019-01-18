package ir.maralani.flighttracker.service;

import ir.maralani.flighttracker.model.Message;

import java.util.List;


public interface MessageService extends BaseService<Message> {
    void sendMessage(String[] userIds, Message message, String[] groupIds);

    List<Message> getByExample(Message message);

    List<Message> getUserMessagesByType(Long userId, Boolean type);
}
