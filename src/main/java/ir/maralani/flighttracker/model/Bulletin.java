package ir.maralani.flighttracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bulletin")
public class Bulletin extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date forecastDate;

    @JsonIgnore
    @JoinColumn
    @ManyToOne(targetEntity = Province.class,fetch = FetchType.EAGER)
    private Province province;


    private Float minTemperature;

    private Float maxTemperature;

    private String phenomena;

    private String icon;

    @Transient
    private String forecastDateString;
    @Transient
    private String title;
    @Transient
    private String provinceName;

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }


    public Float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(String phenomena) {
        this.phenomena = phenomena;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getForecastDateString() {
        return forecastDateString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setForecastDateString(String forecastDateString) {
        this.forecastDateString = forecastDateString;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bulletin)) return false;

        Bulletin bulletin = (Bulletin) o;

        return getId() != null ? getId().equals(bulletin.getId()) : bulletin.getId() == null && (getForecastDate() != null ? getForecastDate().equals(bulletin.getForecastDate()) : bulletin.getForecastDate() == null && (getProvince() != null ? getProvince().equals(bulletin.getProvince()) : bulletin.getProvince() == null && (getMinTemperature() != null ? getMinTemperature().equals(bulletin.getMinTemperature()) : bulletin.getMinTemperature() == null && (getMaxTemperature() != null ? getMaxTemperature().equals(bulletin.getMaxTemperature()) : bulletin.getMaxTemperature() == null && (getPhenomena() != null ? getPhenomena().equals(bulletin.getPhenomena()) : bulletin.getPhenomena() == null && (icon != null ? icon.equals(bulletin.icon) : bulletin.icon == null))))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getForecastDate() != null ? getForecastDate().hashCode() : 0);
        result = 31 * result + (getProvince() != null ? getProvince().hashCode() : 0);
        result = 31 * result + (getMinTemperature() != null ? getMinTemperature().hashCode() : 0);
        result = 31 * result + (getMaxTemperature() != null ? getMaxTemperature().hashCode() : 0);
        result = 31 * result + (getPhenomena() != null ? getPhenomena().hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bulletin{" +
                "id=" + id +
                ", forecastDate=" + forecastDate +
                ", province=" + province +
                ", minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", phenomena='" + phenomena + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
