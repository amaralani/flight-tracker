package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "application_user") // User is a reserved word
public class User extends BaseModel {
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String personNumber;
    @Column(length = 10)
    private String nationalCode;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    private Boolean enabled;
    private Boolean passwordExpired;
    private Boolean locked;
    @Temporal(TemporalType.DATE)
    private Date passwordUpdatedDate;

    private Set<Role> roles;

    private Long userGroupId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getPasswordUpdatedDate() {
        return passwordUpdatedDate;
    }

    public void setPasswordUpdatedDate(Date passwordUpdatedDate) {
        this.passwordUpdatedDate = passwordUpdatedDate;
    }

    public Long getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Long userGroupId) {
        this.userGroupId = userGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) return false;
        if (getUsername() != null ? !getUsername().equals(user.getUsername()) : user.getUsername() != null)
            return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getPersonNumber() != null ? !getPersonNumber().equals(user.getPersonNumber()) : user.getPersonNumber() != null)
            return false;
        if (getNationalCode() != null ? !getNationalCode().equals(user.getNationalCode()) : user.getNationalCode() != null)
            return false;
        if (getCreateDate() != null ? !getCreateDate().equals(user.getCreateDate()) : user.getCreateDate() != null)
            return false;
        if (getEnabled() != null ? !getEnabled().equals(user.getEnabled()) : user.getEnabled() != null) return false;
        if (getPasswordExpired() != null ? !getPasswordExpired().equals(user.getPasswordExpired()) : user.getPasswordExpired() != null)
            return false;
        if (getLocked() != null ? !getLocked().equals(user.getLocked()) : user.getLocked() != null) return false;
        if (getPasswordUpdatedDate() != null ? !getPasswordUpdatedDate().equals(user.getPasswordUpdatedDate()) : user.getPasswordUpdatedDate() != null)
            return false;
        if (getRoles() != null ? !getRoles().equals(user.getRoles()) : user.getRoles() != null) return false;
        return getUserGroupId() != null ? getUserGroupId().equals(user.getUserGroupId()) : user.getUserGroupId() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPersonNumber() != null ? getPersonNumber().hashCode() : 0);
        result = 31 * result + (getNationalCode() != null ? getNationalCode().hashCode() : 0);
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getEnabled() != null ? getEnabled().hashCode() : 0);
        result = 31 * result + (getPasswordExpired() != null ? getPasswordExpired().hashCode() : 0);
        result = 31 * result + (getLocked() != null ? getLocked().hashCode() : 0);
        result = 31 * result + (getPasswordUpdatedDate() != null ? getPasswordUpdatedDate().hashCode() : 0);
        result = 31 * result + (getRoles() != null ? getRoles().hashCode() : 0);
        result = 31 * result + (getUserGroupId() != null ? getUserGroupId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personNumber='" + personNumber + '\'' +
                ", nationalCode='" + nationalCode + '\'' +
                ", createDate=" + createDate +
                ", enabled=" + enabled +
                ", passwordExpired=" + passwordExpired +
                ", locked=" + locked +
                ", passwordUpdatedDate=" + passwordUpdatedDate +
                ", roles=" + roles +
                ", userGroupId=" + userGroupId +
                '}';
    }
}