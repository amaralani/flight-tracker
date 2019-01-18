package ir.maralani.flighttracker.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_session_information")
public class UserSessionInformation extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private String ip;

    private String sessionId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSessionInformation)) return false;

        UserSessionInformation that = (UserSessionInformation) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && (getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null && (getIp() != null ? getIp().equals(that.getIp()) : that.getIp() == null && (getSessionId() != null ? getSessionId().equals(that.getSessionId()) : that.getSessionId() == null && (getStartDate() != null ? getStartDate().equals(that.getStartDate()) : that.getStartDate() == null && (getEndDate() != null ? getEndDate().equals(that.getEndDate()) : that.getEndDate() == null)))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getSessionId() != null ? getSessionId().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSessionInformation{" +
                "id=" + id +
                ", user=" + user +
                ", ip='" + ip + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
