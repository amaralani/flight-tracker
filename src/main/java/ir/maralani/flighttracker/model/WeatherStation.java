package ir.maralani.flighttracker.model;


import javax.persistence.*;

@Entity
@Table(name = "weather_station")
public class WeatherStation extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 4)
    private String provinceCode;
    @Column(length = 50)
    private String farsiName;
    @Column(length = 50)
    private String name;
    @Column(length = 4)
    private String stationId;
    @Column(length = 5)
    private Integer stationNo;

    private String latitude;
    private String longitude;
    private String altitude;
    private Integer level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getFarsiName() {
        return farsiName;
    }

    public void setFarsiName(String farsiName) {
        this.farsiName = farsiName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Integer getStationNo() {
        return stationNo;
    }

    public void setStationNo(Integer stationNo) {
        this.stationNo = stationNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeatherStation)) return false;

        WeatherStation that = (WeatherStation) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null && (getProvinceCode() != null ? getProvinceCode().equals(that.getProvinceCode()) : that.getProvinceCode() == null && (getFarsiName() != null ? getFarsiName().equals(that.getFarsiName()) : that.getFarsiName() == null && (getName() != null ? getName().equals(that.getName()) : that.getName() == null && (getStationId() != null ? getStationId().equals(that.getStationId()) : that.getStationId() == null && (getStationNo() != null ? getStationNo().equals(that.getStationNo()) : that.getStationNo() == null && (getLatitude() != null ? getLatitude().equals(that.getLatitude()) : that.getLatitude() == null && (getLongitude() != null ? getLongitude().equals(that.getLongitude()) : that.getLongitude() == null && (getAltitude() != null ? getAltitude().equals(that.getAltitude()) : that.getAltitude() == null && (getLevel() != null ? getLevel().equals(that.getLevel()) : that.getLevel() == null)))))))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getProvinceCode() != null ? getProvinceCode().hashCode() : 0);
        result = 31 * result + (getFarsiName() != null ? getFarsiName().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStationId() != null ? getStationId().hashCode() : 0);
        result = 31 * result + (getStationNo() != null ? getStationNo().hashCode() : 0);
        result = 31 * result + (getLatitude() != null ? getLatitude().hashCode() : 0);
        result = 31 * result + (getLongitude() != null ? getLongitude().hashCode() : 0);
        result = 31 * result + (getAltitude() != null ? getAltitude().hashCode() : 0);
        result = 31 * result + (getLevel() != null ? getLevel().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WeatherStation{" +
                "id=" + id +
                ", provinceCode='" + provinceCode + '\'' +
                ", farsiName='" + farsiName + '\'' +
                ", name='" + name + '\'' +
                ", stationId='" + stationId + '\'' +
                ", stationNo=" + stationNo +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", level=" + level +
                '}';
    }
}
