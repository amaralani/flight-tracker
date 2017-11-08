package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "data_file")
public class DataFile extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date processStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date processEndDate;

    private String fileType;


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

    public Date getProcessStartDate() {
        return processStartDate;
    }

    public void setProcessStartDate(Date processStartDate) {
        this.processStartDate = processStartDate;
    }

    public Date getProcessEndDate() {
        return processEndDate;
    }

    public void setProcessEndDate(Date processEndDate) {
        this.processEndDate = processEndDate;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataFile)) return false;

        DataFile dataFile = (DataFile) o;

        return getId() != null ? getId().equals(dataFile.getId()) : dataFile.getId() == null && (getFileName() != null ? getFileName().equals(dataFile.getFileName()) : dataFile.getFileName() == null && (getProcessStartDate() != null ? getProcessStartDate().equals(dataFile.getProcessStartDate()) : dataFile.getProcessStartDate() == null && (getProcessEndDate() != null ? getProcessEndDate().equals(dataFile.getProcessEndDate()) : dataFile.getProcessEndDate() == null && (getFileType() != null ? getFileType().equals(dataFile.getFileType()) : dataFile.getFileType() == null))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFileName() != null ? getFileName().hashCode() : 0);
        result = 31 * result + (getProcessStartDate() != null ? getProcessStartDate().hashCode() : 0);
        result = 31 * result + (getProcessEndDate() != null ? getProcessEndDate().hashCode() : 0);
        result = 31 * result + (getFileType() != null ? getFileType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DataFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", processStartDate=" + processStartDate +
                ", processEndDate=" + processEndDate +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
