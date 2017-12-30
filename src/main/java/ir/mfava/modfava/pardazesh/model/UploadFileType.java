package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

@Entity
@Table(name = "upload_file_type")
public class UploadFileType extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadFileType)) return false;

        UploadFileType that = (UploadFileType) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && (getName() != null ? getName().equals(that.getName()) : that.getName() == null && (getActive() != null ? getActive().equals(that.getActive()) : that.getActive() == null));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getActive() != null ? getActive().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UploadFileType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                '}';
    }
}
