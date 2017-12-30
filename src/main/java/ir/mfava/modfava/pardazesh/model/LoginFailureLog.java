package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "login_failure_log")
public class LoginFailureLog extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
        if (!(o instanceof LoginFailureLog)) return false;

        LoginFailureLog that = (LoginFailureLog) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && (getUsername() != null ? getUsername().equals(that.getUsername()) : that.getUsername() == null && (getDateTime() != null ? getDateTime().equals(that.getDateTime()) : that.getDateTime() == null && (getIp() != null ? getIp().equals(that.getIp()) : that.getIp() == null)));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
        result = 31 * result + (getIp() != null ? getIp().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginFailureLog{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", dateTime=" + dateTime +
                ", ip='" + ip + '\'' +
                '}';
    }
}
