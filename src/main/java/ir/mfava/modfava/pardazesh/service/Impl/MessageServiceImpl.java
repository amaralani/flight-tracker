package ir.mfava.modfava.pardazesh.service.Impl;

import ir.mfava.modfava.pardazesh.model.Message;
import ir.mfava.modfava.pardazesh.model.User;
import ir.mfava.modfava.pardazesh.repository.MessageRepository;
import ir.mfava.modfava.pardazesh.service.MessageService;
import ir.mfava.modfava.pardazesh.service.UserGroupService;
import ir.mfava.modfava.pardazesh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;

    @Override
    public boolean exists(Message entity) {
        return messageRepository.exists(Example.of(entity));
    }

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    @Override
    public Message getById(Long id) {
        return messageRepository.getOne(id);
    }

    @Override
    public Message save(Message entity) {
        return messageRepository.save(entity);
    }

    @Override
    @Transactional
    public void sendMessage(String[] userIds, Message messageExample, String[] groupIds) {
        for (String stringUserId : userIds) {
            saveNewMessage(messageExample, userService.getById(Long.valueOf(stringUserId)));
        }
        for (String groupId : groupIds) {
            for (User user: userService.getUsersByGroupId(Long.valueOf(groupId))) {
                saveNewMessage(messageExample, user);
            }
        }
    }

    private void saveNewMessage(Message messageExample, User user) {
        Message message = new Message();
        message.setCreateDate(messageExample.getCreateDate());
        message.setSubject(messageExample.getSubject());
        message.setText(messageExample.getText());
        message.setDeleted(messageExample.getDeleted());
        message.setRead(messageExample.getRead());
        message.setSender(messageExample.getSender());
        message.setReceiver(user);
        save(message);
    }

    @Override
    public void remove(Message entity) {
        messageRepository.delete(entity);
    }

    @Override
    public List<Message> getByExample(Message message) {
        return messageRepository.findAll(Example.of(message));
    }

    @Override
    public List<Message> getUserMessagesByType(Long userId, Boolean type) {
        return messageRepository.getByReceiverIdAndType(userId, type);
    }
}
