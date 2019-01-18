package ir.maralani.flighttracker.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "upload_file")
public class UploadFile extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    private String title;

    @ManyToOne
    @JoinColumn
    private UploadFileType fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UploadFileType getFileType() {
        return fileType;
    }

    public void setFileType(UploadFileType fileType) {
        this.fileType = fileType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadFile)) return false;

        UploadFile that = (UploadFile) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && (getFileName() != null ? getFileName().equals(that.getFileName()) : that.getFileName() == null && (getTitle() != null ? getTitle().equals(that.getTitle()) : that.getTitle() == null && (getFileType() != null ? getFileType().equals(that.getFileType()) : that.getFileType() == null && (getCreateDate() != null ? getCreateDate().equals(that.getCreateDate()) : that.getCreateDate() == null && (getDeleted() != null ? getDeleted().equals(that.getDeleted()) : that.getDeleted() == null)))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getFileType() != null ? getFileType().hashCode() : 0);
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getDeleted() != null ? getDeleted().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", title='" + title + '\'' +
                ", fileType=" + fileType +
                ", createDate=" + createDate +
                ", deleted=" + deleted +
                '}';
    }
}
