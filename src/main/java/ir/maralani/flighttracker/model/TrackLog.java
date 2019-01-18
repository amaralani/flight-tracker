package ir.maralani.flighttracker.model;

import javax.persistence.*;

@Entity
@Table(name = "tracklog")
public class TrackLog extends BaseModel{
    @Id
    @Column(name = "tracklog_id")
    private Long id;

    @Column(name = "tracklog_code")
    private String code;

    @Column(name = "tracklog_codes")
    private String codes;

    @Column(name = "tracklog_latitude")
    private Float latitude;

    @Column(name = "tracklog_longitude")
    private Float longitude;

    @Column(name = "tracklog_heading")
    private Integer heading;

    @Column(name = "tracklog_altitude")
    private Float altitude;

    @Column(name = "tracklog_speed")
    private Integer speed;

    @Column(name = "tracklog_col1")
    private String col1;

    @Column(name = "tracklog_radar")
    private String radar;

    @Column(name = "tracklog_col2")
    private String col2;

    @Column(name = "tracklog_type")
    private String type;

    @Column(name = "tracklog_col3")
    private String col3;

    @Column(name = "tracklog_time")
    private Long time;

    @Column(name = "tracklog_source")
    private String source;

    @Column(name = "tracklog_destination")
    private String destination;

    @Column(name = "tracklog_col4")
    private String col4;

    @Column(name = "tracklog_col5")
    private String col5;

    @Column(name = "tracklog_col6")
    private String col6;

    @Column(name = "tracklog_col7")
    private String col7;

    @Column(name = "tracklog_col8")
    private String col8;

    @Column(name = "tracklog_receivetime")
    private String receiveTime;

    @Transient
    private String detailedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getHeading() {
        return heading;
    }

    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    public Float getAltitude() {
        return altitude;
    }

    public void setAltitude(Float altitude) {
        this.altitude = altitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getRadar() {
        return radar;
    }

    public void setRadar(String radar) {
        this.radar = radar;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4;
    }

    public String getCol5() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5;
    }

    public String getCol6() {
        return col6;
    }

    public void setCol6(String col6) {
        this.col6 = col6;
    }

    public String getCol7() {
        return col7;
    }

    public void setCol7(String col7) {
        this.col7 = col7;
    }

    public String getCol8() {
        return col8;
    }

    public void setCol8(String col8) {
        this.col8 = col8;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getDetailedTime() {
        return detailedTime;
    }

    public void setDetailedTime(String detailedTime) {
        this.detailedTime = detailedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;

        Track track = (Track) o;

        if (getId() != null ? !getId().equals(track.getId()) : track.getId() != null) return false;
        if (getCode() != null ? !getCode().equals(track.getCode()) : track.getCode() != null) return false;
        if (getCodes() != null ? !getCodes().equals(track.getCodes()) : track.getCodes() != null) return false;
        if (getLatitude() != null ? !getLatitude().equals(track.getLatitude()) : track.getLatitude() != null)
            return false;
        if (getLongitude() != null ? !getLongitude().equals(track.getLongitude()) : track.getLongitude() != null)
            return false;
        if (getHeading() != null ? !getHeading().equals(track.getHeading()) : track.getHeading() != null) return false;
        if (getAltitude() != null ? !getAltitude().equals(track.getAltitude()) : track.getAltitude() != null)
            return false;
        if (getSpeed() != null ? !getSpeed().equals(track.getSpeed()) : track.getSpeed() != null) return false;
        if (getCol1() != null ? !getCol1().equals(track.getCol1()) : track.getCol1() != null) return false;
        if (getRadar() != null ? !getRadar().equals(track.getRadar()) : track.getRadar() != null) return false;
        if (getCol2() != null ? !getCol2().equals(track.getCol2()) : track.getCol2() != null) return false;
        if (getType() != null ? !getType().equals(track.getType()) : track.getType() != null) return false;
        if (getCol3() != null ? !getCol3().equals(track.getCol3()) : track.getCol3() != null) return false;
        if (getTime() != null ? !getTime().equals(track.getTime()) : track.getTime() != null) return false;
        if (getSource() != null ? !getSource().equals(track.getSource()) : track.getSource() != null) return false;
        if (getDestination() != null ? !getDestination().equals(track.getDestination()) : track.getDestination() != null)
            return false;
        if (getCol4() != null ? !getCol4().equals(track.getCol4()) : track.getCol4() != null) return false;
        if (getCol5() != null ? !getCol5().equals(track.getCo5()) : track.getCo5() != null) return false;
        if (getCol6() != null ? !getCol6().equals(track.getCol6()) : track.getCol6() != null) return false;
        if (getCol7() != null ? !getCol7().equals(track.getCol7()) : track.getCol7() != null) return false;
        if (getCol8() != null ? !getCol8().equals(track.getCol8()) : track.getCol8() != null) return false;
        return getReceiveTime() != null ? getReceiveTime().equals(track.getReceiveTime()) : track.getReceiveTime() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getCodes() != null ? getCodes().hashCode() : 0);
        result = 31 * result + (getLatitude() != null ? getLatitude().hashCode() : 0);
        result = 31 * result + (getLongitude() != null ? getLongitude().hashCode() : 0);
        result = 31 * result + (getHeading() != null ? getHeading().hashCode() : 0);
        result = 31 * result + (getAltitude() != null ? getAltitude().hashCode() : 0);
        result = 31 * result + (getSpeed() != null ? getSpeed().hashCode() : 0);
        result = 31 * result + (getCol1() != null ? getCol1().hashCode() : 0);
        result = 31 * result + (getRadar() != null ? getRadar().hashCode() : 0);
        result = 31 * result + (getCol2() != null ? getCol2().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getCol3() != null ? getCol3().hashCode() : 0);
        result = 31 * result + (getTime() != null ? getTime().hashCode() : 0);
        result = 31 * result + (getSource() != null ? getSource().hashCode() : 0);
        result = 31 * result + (getDestination() != null ? getDestination().hashCode() : 0);
        result = 31 * result + (getCol4() != null ? getCol4().hashCode() : 0);
        result = 31 * result + (getCol5() != null ? getCol5().hashCode() : 0);
        result = 31 * result + (getCol6() != null ? getCol6().hashCode() : 0);
        result = 31 * result + (getCol7() != null ? getCol7().hashCode() : 0);
        result = 31 * result + (getCol8() != null ? getCol8().hashCode() : 0);
        result = 31 * result + (getReceiveTime() != null ? getReceiveTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", codes='" + codes + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", heading=" + heading +
                ", altitude=" + altitude +
                ", speed=" + speed +
                ", col1='" + col1 + '\'' +
                ", radar='" + radar + '\'' +
                ", col2='" + col2 + '\'' +
                ", type='" + type + '\'' +
                ", col3='" + col3 + '\'' +
                ", time=" + time +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", col4='" + col4 + '\'' +
                ", col5='" + col5 + '\'' +
                ", col6='" + col6 + '\'' +
                ", col7='" + col7 + '\'' +
                ", col8='" + col8 + '\'' +
                ", receiveTime='" + receiveTime + '\'' +
                '}';
    }
}
