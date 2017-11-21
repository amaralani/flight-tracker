package ir.mfava.modfava.pardazesh.model.DTO;

import ir.mfava.modfava.pardazesh.model.Message;
import ir.mfava.modfava.pardazesh.util.DateUtils;

public class MessageDTO {

    private Long id;
    private String senderName;
    private String createDate;
    private String subject;
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static MessageDTO fromEntity(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setSenderName(message.getSender().getFirstName() + " " + message.getSender().getLastName());
        messageDTO.setSubject(message.getSubject());
        messageDTO.setText(message.getText());
//        messageDTO.setCreateDate(DateUtils.convertJulianToPersian(message.getCreateDate(),"HH:mm:SS YYYY-MM-dd "));
        messageDTO.setCreateDate(DateUtils.convertJulianToPersian(message.getCreateDate(),"HH:mm:SS EEE dd MMMM YYYY "));
        return messageDTO;
    }
}
