package ir.mfava.modfava.pardazesh.controller;

import ir.mfava.modfava.pardazesh.model.Constants;
import ir.mfava.modfava.pardazesh.model.DTO.JSONMessage;
import ir.mfava.modfava.pardazesh.model.DTO.MessageDTO;
import ir.mfava.modfava.pardazesh.model.Message;
import ir.mfava.modfava.pardazesh.model.report.event.Event;
import ir.mfava.modfava.pardazesh.service.MessageService;
import ir.mfava.modfava.pardazesh.service.UserGroupService;
import ir.mfava.modfava.pardazesh.service.UserService;
import ir.mfava.modfava.pardazesh.service.report.event.EventService;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping(value = "/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private EventService eventService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String viewMessagesPage(ModelMap map, HttpSession session, Authentication authentication) {
        Long userId = getUserId(authentication);
        List<Message> messageList = messageService.getUserMessagesByType(userId, Constants.MessageStatus.UNREAD);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message messageForChange : messageList) {
            messageDTOs.add(MessageDTO.fromEntity(messageForChange));
        }
        map.put("userMessages", messageDTOs);
        map.put("users", userService.getAll());
        map.put("userGroups", userGroupService.getAll());
        map.put("successMessage", session.getAttribute("successMessage"));
        map.put("errorMessage", session.getAttribute("errorMessage"));
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
        return "messages";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveMessage(@RequestParam(name = "receiverIds") String commaSeparatedReceiverIds,
                              @RequestParam(name = "userGroupIds") String userGroupIds,
                              @RequestParam(name = "subject") String subject,
                              @RequestParam(name = "text") String text,
                              Authentication authentication,
                              HttpServletRequest request,
                              HttpSession session) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Message");
        descriptionMap.put("receiverIds", commaSeparatedReceiverIds);
        descriptionMap.put("userGroupIds", userGroupIds);
        descriptionMap.put("subject", subject);
        descriptionMap.put("text", text);

        String[] stringReceiverIds =  commaSeparatedReceiverIds.split(",");
        String[] stringUserGroupIds =  userGroupIds.split(",");

        Message message = new Message();
        message.setSender(getUser(authentication));
        message.setCreateDate(new Date());
        message.setDeleted(false);
        message.setRead(false);
        message.setText(text);
        message.setSubject(subject);
        Event.Flag flag;
        try {
            messageService.sendMessage(stringReceiverIds,message, stringUserGroupIds);
            session.setAttribute("successMessage", "پیام با موفقیت ارسال شد.");
            flag = Event.Flag.SUCCESS;
        } catch (Exception ex) {
            ex.printStackTrace();
            session.setAttribute("errorMessage", "خطا در ارسال پیام.");
            flag = Event.Flag.FAILURE;
        }
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.NEW_DATA, flag, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return "redirect:/message/";
    }

    @ResponseBody
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public JSONMessage readMessage(@RequestParam(name = "messageId") Long messageId,
                                   HttpServletRequest request,
                                   Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Message");
        descriptionMap.put("messageId", String.valueOf(messageId));
        Message message = messageService.getById(messageId);
        if (message == null) {
            return JSONMessage.Error("message.not.exists");
        }
        message.setRead(true);
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.ADD_EDIT, Event.SubType.EDIT_DATA, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return saveMessage(message);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public JSONMessage deleteMessage(@RequestParam(name = "messageId") Long messageId,
                                     HttpServletRequest request,
                                     Authentication authentication) {
        Map<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("Entity","Message");
        descriptionMap.put("messageId", String.valueOf(messageId));

        Message message = messageService.getById(messageId);
        if (message == null) {
            return JSONMessage.Error("message.not.exists");
        }
        message.setDeleted(true);
        eventService.addEvent(request.getLocalAddr(), request.getLocalName(), request.getRemoteAddr(),request.getRemoteHost(), request.getRequestURI(), getUser(authentication).getUsername(), Event.ActionType.DELETE, Event.SubType.EDIT_DATA, Event.Flag.SUCCESS, descriptionMap, Event.Sensitivity.NOTIFICATION);
        return saveMessage(message);
    }

    private JSONMessage saveMessage(Message message) {
        try {
            messageService.save(message);
            return JSONMessage.Success();
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONMessage.Error("exception");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JSONMessage remove(Authentication authentication,
                              @RequestParam(name = "type") String type) {
        List<Message> messageList;
        if ("READ".equals(type)) {
            messageList = messageService.getUserMessagesByType(getUserId(authentication), Constants.MessageStatus.READ);
        } else if ("UNREAD".equals(type)) {
            messageList = messageService.getUserMessagesByType(getUserId(authentication), Constants.MessageStatus.UNREAD);
        } else {
            messageList = messageService.getUserMessagesByType(getUserId(authentication), Constants.MessageStatus.BOTH);
        }

        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message messageForChange : messageList) {
            messageDTOs.add(MessageDTO.fromEntity(messageForChange));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", messageDTOs);
        return JSONMessage.Success(map);
    }
}
