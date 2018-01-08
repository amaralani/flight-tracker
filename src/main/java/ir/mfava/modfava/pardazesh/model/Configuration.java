package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class Configuration extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer sessionTimeoutInSeconds;

    private Integer passwordLength;

    private Integer passwordResetPeriod;

    private Integer loginFailureAccountLockCount;

    private Integer loginFailureDelayTimeInSeconds;

    @Enumerated(EnumType.ORDINAL)
    private PasswordComplexity passwordComplexity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSessionTimeoutInSeconds() {
        return sessionTimeoutInSeconds;
    }

    public void setSessionTimeoutInSeconds(Integer sessionTimeoutInSeconds) {
        this.sessionTimeoutInSeconds = sessionTimeoutInSeconds;
    }

    public Integer getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(Integer passwordLength) {
        this.passwordLength = passwordLength;
    }

    public Integer getPasswordResetPeriod() {
        return passwordResetPeriod;
    }

    public void setPasswordResetPeriod(Integer passwordResetPeriod) {
        this.passwordResetPeriod = passwordResetPeriod;
    }

    public Integer getLoginFailureAccountLockCount() {
        return loginFailureAccountLockCount;
    }

    public void setLoginFailureAccountLockCount(Integer loginFailureAccountLockCount) {
        this.loginFailureAccountLockCount = loginFailureAccountLockCount;
    }

    public Integer getLoginFailureDelayTimeInSeconds() {
        return loginFailureDelayTimeInSeconds;
    }

    public void setLoginFailureDelayTimeInSeconds(Integer loginFailureDelayTimeInSeconds) {
        this.loginFailureDelayTimeInSeconds = loginFailureDelayTimeInSeconds;
    }

    public PasswordComplexity getPasswordComplexity() {
        return passwordComplexity;
    }

    public void setPasswordComplexity(PasswordComplexity passwordComplexity) {
        this.passwordComplexity = passwordComplexity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Configuration)) return false;

        Configuration that = (Configuration) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getSessionTimeoutInSeconds() != null ? !getSessionTimeoutInSeconds().equals(that.getSessionTimeoutInSeconds()) : that.getSessionTimeoutInSeconds() != null)
            return false;
        if (getPasswordLength() != null ? !getPasswordLength().equals(that.getPasswordLength()) : that.getPasswordLength() != null)
            return false;
        if (getPasswordResetPeriod() != null ? !getPasswordResetPeriod().equals(that.getPasswordResetPeriod()) : that.getPasswordResetPeriod() != null)
            return false;
        if (getLoginFailureAccountLockCount() != null ? !getLoginFailureAccountLockCount().equals(that.getLoginFailureAccountLockCount()) : that.getLoginFailureAccountLockCount() != null)
            return false;
        if (getLoginFailureDelayTimeInSeconds() != null ? !getLoginFailureDelayTimeInSeconds().equals(that.getLoginFailureDelayTimeInSeconds()) : that.getLoginFailureDelayTimeInSeconds() != null)
            return false;
        return getPasswordComplexity() == that.getPasswordComplexity();

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getSessionTimeoutInSeconds() != null ? getSessionTimeoutInSeconds().hashCode() : 0);
        result = 31 * result + (getPasswordLength() != null ? getPasswordLength().hashCode() : 0);
        result = 31 * result + (getPasswordResetPeriod() != null ? getPasswordResetPeriod().hashCode() : 0);
        result = 31 * result + (getLoginFailureAccountLockCount() != null ? getLoginFailureAccountLockCount().hashCode() : 0);
        result = 31 * result + (getLoginFailureDelayTimeInSeconds() != null ? getLoginFailureDelayTimeInSeconds().hashCode() : 0);
        result = 31 * result + (getPasswordComplexity() != null ? getPasswordComplexity().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", sessionTimeoutInSeconds=" + sessionTimeoutInSeconds +
                ", passwordLength=" + passwordLength +
                ", passwordResetPeriod=" + passwordResetPeriod +
                ", loginFailureAccountLockCount=" + loginFailureAccountLockCount +
                ", loginFailureDelayTimeInSeconds=" + loginFailureDelayTimeInSeconds +
                ", passwordComplexity=" + passwordComplexity +
                '}';
    }

    public enum PasswordComplexity {
        MINIMUM,
        WITH_NUMBER,
        UPPER_LOWER_CASE,
        UPPER_LOWER_NUMBER,
        UPPER_LOWER_NUMBER_CHARACTER
    }
}
