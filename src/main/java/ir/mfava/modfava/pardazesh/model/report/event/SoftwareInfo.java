package ir.mfava.modfava.pardazesh.model.report.event;

import ir.mfava.modfava.pardazesh.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "software_info")
public class SoftwareInfo extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String softwareId;
    private String softwareName;
    private String softwareVersion;

    @Column(updatable = false)
    private String userName;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastConnectionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(Date lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SoftwareInfo)) return false;

        SoftwareInfo that = (SoftwareInfo) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getSoftwareId() != null ? !getSoftwareId().equals(that.getSoftwareId()) : that.getSoftwareId() != null)
            return false;
        if (getSoftwareName() != null ? !getSoftwareName().equals(that.getSoftwareName()) : that.getSoftwareName() != null)
            return false;
        if (getSoftwareVersion() != null ? !getSoftwareVersion().equals(that.getSoftwareVersion()) : that.getSoftwareVersion() != null)
            return false;
        if (getUserName() != null ? !getUserName().equals(that.getUserName()) : that.getUserName() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null)
            return false;
        return getLastConnectionDate() != null ? getLastConnectionDate().equals(that.getLastConnectionDate()) : that.getLastConnectionDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSoftwareId() != null ? getSoftwareId().hashCode() : 0);
        result = 31 * result + (getSoftwareName() != null ? getSoftwareName().hashCode() : 0);
        result = 31 * result + (getSoftwareVersion() != null ? getSoftwareVersion().hashCode() : 0);
        result = 31 * result + (getUserName() != null ? getUserName().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getLastConnectionDate() != null ? getLastConnectionDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SoftwareInfo{" +
                "id=" + id +
                ", softwareId='" + softwareId + '\'' +
                ", softwareName='" + softwareName + '\'' +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", lastConnectionDate=" + lastConnectionDate +
                '}';
    }
}
