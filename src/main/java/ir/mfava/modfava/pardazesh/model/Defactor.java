package ir.mfava.modfava.pardazesh.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.mfava.modfava.pardazesh.util.DateUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "defactor")
public class Defactor extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @JoinColumn
    @ManyToOne(targetEntity = Province.class, fetch = FetchType.EAGER)
    private Province province;

    private Integer hoursFrom;
    private Integer hoursTo;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String persianDate;

    private Integer height300;
    private Integer temperature300;
    private Integer windSpeed300;
    private Integer windDirection300;

    private Integer height500;
    private Integer temperature500;
    private Integer windSpeed500;
    private Integer windDirection500;

    private Integer height700;
    private Integer temperature700;
    private Integer windSpeed700;
    private Integer windDirection700;

    private Boolean visible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getProvinceName() {
        if (this.getProvince() == null) {
            return "";
        }
        return this.getProvince().getName();
    }

    public Integer getHoursFrom() {
        return hoursFrom;
    }

    public void setHoursFrom(Integer hoursFrom) {
        this.hoursFrom = hoursFrom;
    }

    public Integer getHoursTo() {
        return hoursTo;
    }

    public void setHoursTo(Integer hoursTo) {
        this.hoursTo = hoursTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHeight300() {
        return height300;
    }

    public void setHeight300(Integer height300) {
        this.height300 = height300;
    }

    public Integer getTemperature300() {
        return temperature300;
    }

    public void setTemperature300(Integer temperature300) {
        this.temperature300 = temperature300;
    }

    public Integer getWindSpeed300() {
        return windSpeed300;
    }

    public void setWindSpeed300(Integer windSpeed300) {
        this.windSpeed300 = windSpeed300;
    }

    public Integer getWindDirection300() {
        return windDirection300;
    }

    public void setWindDirection300(Integer windDirection300) {
        this.windDirection300 = windDirection300;
    }

    public String getWindSpeedDirection300() {
        return windDirection300 + "/" + windSpeed300;
    }

    public Integer getHeight500() {
        return height500;
    }

    public void setHeight500(Integer height500) {
        this.height500 = height500;
    }

    public Integer getTemperature500() {
        return temperature500;
    }

    public void setTemperature500(Integer temperature500) {
        this.temperature500 = temperature500;
    }

    public Integer getWindSpeed500() {
        return windSpeed500;
    }

    public void setWindSpeed500(Integer windSpeed500) {
        this.windSpeed500 = windSpeed500;
    }

    public Integer getWindDirection500() {
        return windDirection500;
    }

    public void setWindDirection500(Integer windDirection500) {
        this.windDirection500 = windDirection500;
    }

    public String getWindSpeedDirection500() {
        return windDirection500 + "/" + windSpeed500;
    }

    public Integer getHeight700() {
        return height700;
    }

    public void setHeight700(Integer height700) {
        this.height700 = height700;
    }

    public Integer getTemperature700() {
        return temperature700;
    }

    public void setTemperature700(Integer temperature700) {
        this.temperature700 = temperature700;
    }

    public Integer getWindSpeed700() {
        return windSpeed700;
    }

    public void setWindSpeed700(Integer windSpeed700) {
        this.windSpeed700 = windSpeed700;
    }

    public Integer getWindDirection700() {
        return windDirection700;
    }

    public void setWindDirection700(Integer windDirection700) {
        this.windDirection700 = windDirection700;
    }

    public String getWindSpeedDirection700() {
        return windDirection700 + "/" + windSpeed700;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Long getProvinceId() {
        return this.province.getId();
    }

    public String getDatePersian(){
        return DateUtils.convertJulianToPersian(this.date);
    }

    public String getPersianDate() {
        return persianDate;
    }

    public void setPersianDate(String persianDate) {
        this.persianDate = persianDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Defactor)) return false;

        Defactor defactor = (Defactor) o;

        return getId() != null ? getId().equals(defactor.getId()) : defactor.getId() == null && (getProvince() != null ? getProvince().equals(defactor.getProvince()) : defactor.getProvince() == null && (getHoursFrom() != null ? getHoursFrom().equals(defactor.getHoursFrom()) : defactor.getHoursFrom() == null && (getHoursTo() != null ? getHoursTo().equals(defactor.getHoursTo()) : defactor.getHoursTo() == null && (getDate() != null ? getDate().equals(defactor.getDate()) : defactor.getDate() == null && (getHeight300() != null ? getHeight300().equals(defactor.getHeight300()) : defactor.getHeight300() == null && (getTemperature300() != null ? getTemperature300().equals(defactor.getTemperature300()) : defactor.getTemperature300() == null && (getWindSpeed300() != null ? getWindSpeed300().equals(defactor.getWindSpeed300()) : defactor.getWindSpeed300() == null && (getWindDirection300() != null ? getWindDirection300().equals(defactor.getWindDirection300()) : defactor.getWindDirection300() == null && (getHeight500() != null ? getHeight500().equals(defactor.getHeight500()) : defactor.getHeight500() == null && (getTemperature500() != null ? getTemperature500().equals(defactor.getTemperature500()) : defactor.getTemperature500() == null && (getWindSpeed500() != null ? getWindSpeed500().equals(defactor.getWindSpeed500()) : defactor.getWindSpeed500() == null && (getWindDirection500() != null ? getWindDirection500().equals(defactor.getWindDirection500()) : defactor.getWindDirection500() == null && (getHeight700() != null ? getHeight700().equals(defactor.getHeight700()) : defactor.getHeight700() == null && (getTemperature700() != null ? getTemperature700().equals(defactor.getTemperature700()) : defactor.getTemperature700() == null && (getWindSpeed700() != null ? getWindSpeed700().equals(defactor.getWindSpeed700()) : defactor.getWindSpeed700() == null && (getWindDirection700() != null ? getWindDirection700().equals(defactor.getWindDirection700()) : defactor.getWindDirection700() == null))))))))))))))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getProvince() != null ? getProvince().hashCode() : 0);
        result = 31 * result + (getHoursFrom() != null ? getHoursFrom().hashCode() : 0);
        result = 31 * result + (getHoursTo() != null ? getHoursTo().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getHeight300() != null ? getHeight300().hashCode() : 0);
        result = 31 * result + (getTemperature300() != null ? getTemperature300().hashCode() : 0);
        result = 31 * result + (getWindSpeed300() != null ? getWindSpeed300().hashCode() : 0);
        result = 31 * result + (getWindDirection300() != null ? getWindDirection300().hashCode() : 0);
        result = 31 * result + (getHeight500() != null ? getHeight500().hashCode() : 0);
        result = 31 * result + (getTemperature500() != null ? getTemperature500().hashCode() : 0);
        result = 31 * result + (getWindSpeed500() != null ? getWindSpeed500().hashCode() : 0);
        result = 31 * result + (getWindDirection500() != null ? getWindDirection500().hashCode() : 0);
        result = 31 * result + (getHeight700() != null ? getHeight700().hashCode() : 0);
        result = 31 * result + (getTemperature700() != null ? getTemperature700().hashCode() : 0);
        result = 31 * result + (getWindSpeed700() != null ? getWindSpeed700().hashCode() : 0);
        result = 31 * result + (getWindDirection700() != null ? getWindDirection700().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Defactor{" +
                "id=" + id +
                ", province=" + province +
                ", hoursFrom=" + hoursFrom +
                ", hoursTo=" + hoursTo +
                ", date=" + date +
                ", persianDate='" + persianDate + '\'' +
                ", height300=" + height300 +
                ", temperature300=" + temperature300 +
                ", windSpeed300=" + windSpeed300 +
                ", windDirection300=" + windDirection300 +
                ", height500=" + height500 +
                ", temperature500=" + temperature500 +
                ", windSpeed500=" + windSpeed500 +
                ", windDirection500=" + windDirection500 +
                ", height700=" + height700 +
                ", temperature700=" + temperature700 +
                ", windSpeed700=" + windSpeed700 +
                ", windDirection700=" + windDirection700 +
                ", visible=" + visible +
                '}';
    }
}
