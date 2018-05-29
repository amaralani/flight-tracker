package ir.mfava.modfava.pardazesh.model.report.event;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "event")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long time;

    private String hostIp;
    private String hostName;
    private String clientIp;
    private String clientName;
    private String url;
    private String username;
    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    @Enumerated(EnumType.STRING)
    private SubType subType;

    @Enumerated(EnumType.STRING)
    private Sensitivity sensitivity;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    private Flag flag;

    @Column(length = 1500)
    private String description;


    public enum ActionType {
        LOGIN_LOGOUT,
        ADD_EDIT,
        REPORT,
        ERROR,
        DELETE,
        USER_MANAGEMENT
    }

    public enum SubType {
        SUCCESS_LOGIN,
        UNSUCCESS_LOGIN,
        SUBSYSTEM_START,
        SUBSYSTEM_STOP,
        INVALID_TOKEN,
        BLOCK_USER,
        CHANGE_PASSWORD,
        USER_LOGIN_LOGOUT,
        NEW_DATA,
        EDIT_DATA,
        CHANGE_SYSTEM_CONFIG,
        CHANGE_SECURITY_CONFIG,
        TAKE_REPORT,
        PRINT_REPORT,
        EXPORT_FILE,
        DB_CONNECTION_ERROR,
        INPUT_VARIABLE_ERROR,
        DB_MATCH_ERROR,
        TRANSACTION_COMMIT_ERROR,
        ACCESS_DENIED_ERROR,
        SESSION_ERROR,
        SYSTEM_ERROR,
        ENCRYPTION_MODULE_ERROR,
        SSL_ERROR,
        TSL_ERROR,
        DELETE_FROM_DB,
        DELETE_FROM_FILE,
        DELETE_ATTACHMENT,
        ADD_NEW_USER,
        DELETE_USER,
        CHANGE_USER_PROFILE,
        CHANGE_USER_ACCESS,
        CHANGE_GROUP,
        ADD_GROUP,
        EDIT_GROUP,
        DELETE_GROUP,
        CHANGE_GROUP_ACCESS,
        ADD_TOKEN,
        DELETE_TOKEN,
        TOKEN_ASSIGNMENT
    }

    public enum Sensitivity {
        NECESSARY,
        ALARM,
        EXCEPTION,
        NOTIFICATION
    }

    public enum Importance {
        NULL,
        NORMAL,
        IMPORTANT,
        SENSITIVE
    }

    public enum Flag {
        SUCCESS,
        FAILURE,
        ERROR
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    public Sensitivity getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (getId() != null ? !getId().equals(event.getId()) : event.getId() != null) return false;
        if (getTime() != null ? !getTime().equals(event.getTime()) : event.getTime() != null) return false;
        if (getHostIp() != null ? !getHostIp().equals(event.getHostIp()) : event.getHostIp() != null) return false;
        if (getHostName() != null ? !getHostName().equals(event.getHostName()) : event.getHostName() != null)
            return false;
        if (getClientIp() != null ? !getClientIp().equals(event.getClientIp()) : event.getClientIp() != null)
            return false;
        if (getClientName() != null ? !getClientName().equals(event.getClientName()) : event.getClientName() != null)
            return false;
        if (getUrl() != null ? !getUrl().equals(event.getUrl()) : event.getUrl() != null) return false;
        if (getUsername() != null ? !getUsername().equals(event.getUsername()) : event.getUsername() != null)
            return false;
        if (getActionType() != event.getActionType()) return false;
        if (getSubType() != event.getSubType()) return false;
        if (getSensitivity() != event.getSensitivity()) return false;
        if (getImportance() != event.getImportance()) return false;
        if (getFlag() != event.getFlag()) return false;
        return getDescription() != null ? getDescription().equals(event.getDescription()) : event.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        result = 31 * result + (getHostIp() != null ? getHostIp().hashCode() : 0);
        result = 31 * result + (getHostName() != null ? getHostName().hashCode() : 0);
        result = 31 * result + (getClientIp() != null ? getClientIp().hashCode() : 0);
        result = 31 * result + (getClientName() != null ? getClientName().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getActionType() != null ? getActionType().hashCode() : 0);
        result = 31 * result + (getSubType() != null ? getSubType().hashCode() : 0);
        result = 31 * result + (getSensitivity() != null ? getSensitivity().hashCode() : 0);
        result = 31 * result + (getImportance() != null ? getImportance().hashCode() : 0);
        result = 31 * result + (getFlag() != null ? getFlag().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", time=" + time +
                ", hostIp='" + hostIp + '\'' +
                ", hostName='" + hostName + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", clientName='" + clientName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", actionType=" + actionType +
                ", subType=" + subType +
                ", sensitivity=" + sensitivity +
                ", importance=" + importance +
                ", flag=" + flag +
                ", description='" + description + '\'' +
                '}';
    }
}
