package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "weather")
public class Weather extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 500)
    private String originalContent;

    @Enumerated(EnumType.ORDINAL)
    private ReportType reportType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @ManyToOne
    @JoinColumn
    private WeatherStation weatherStation;

    @ManyToOne
    @JoinColumn
    private Phenomena phenomena;

    private String text;
    private String visibility;
    private String verticalVisibility;
    private String rvr;
    private String cloud;
    private String fileName;
    private Integer windDirection = null;
    private Integer windDirectionMin = null;
    private Integer windDirectionMax = null;
    private boolean windDirectionIsVariable = false;
    private Float windSpeed = null; // (in knots x 1.1508 = MPH)
    private Float windGusts = null; // (in knots x 1.1508 = MPH)
    private boolean isCavok = false;
    private Float visibilityMiles = null; // in miles
    private Float visibilityKilometers = null; // in kilometers
    private Float visibilityMeters = null; // in meters
    private boolean visibilityLessThan = false;
    private Float pressure = null;
    private Float temperature = null;
    private Float temperaturePrecise = null;
    private Float dewPoint = null;
    private Float dewPointPrecise = null;
    private boolean isCumulonimbus = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public WeatherStation getWeatherStation() {
        return weatherStation;
    }

    public void setWeatherStation(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
    }

    public Phenomena getPhenomena() {
        return phenomena;
    }

    public void setPhenomena(Phenomena phenomena) {
        this.phenomena = phenomena;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getVerticalVisibility() {
        return verticalVisibility;
    }

    public void setVerticalVisibility(String verticalVisibility) {
        this.verticalVisibility = verticalVisibility;
    }

    public String getRvr() {
        return rvr;
    }

    public void setRvr(String rvr) {
        this.rvr = rvr;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Integer windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getWindDirectionMin() {
        return windDirectionMin;
    }

    public void setWindDirectionMin(Integer windDirectionMin) {
        this.windDirectionMin = windDirectionMin;
    }

    public Integer getWindDirectionMax() {
        return windDirectionMax;
    }

    public void setWindDirectionMax(Integer windDirectionMax) {
        this.windDirectionMax = windDirectionMax;
    }

    public boolean isWindDirectionIsVariable() {
        return windDirectionIsVariable;
    }

    public void setWindDirectionIsVariable(boolean windDirectionIsVariable) {
        this.windDirectionIsVariable = windDirectionIsVariable;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getWindGusts() {
        return windGusts;
    }

    public void setWindGusts(Float windGusts) {
        this.windGusts = windGusts;
    }

    public boolean isCavok() {
        return isCavok;
    }

    public void setCavok(boolean cavok) {
        isCavok = cavok;
    }

    public Float getVisibilityMiles() {
        return visibilityMiles;
    }

    public void setVisibilityMiles(Float visibilityMiles) {
        this.visibilityMiles = visibilityMiles;
    }

    public Float getVisibilityKilometers() {
        return visibilityKilometers;
    }

    public void setVisibilityKilometers(Float visibilityKilometers) {
        this.visibilityKilometers = visibilityKilometers;
    }

    public Float getVisibilityMeters() {
        return visibilityMeters;
    }

    public void setVisibilityMeters(Float visibilityMeters) {
        this.visibilityMeters = visibilityMeters;
    }

    public boolean isVisibilityLessThan() {
        return visibilityLessThan;
    }

    public void setVisibilityLessThan(boolean visibilityLessThan) {
        this.visibilityLessThan = visibilityLessThan;
    }

    public Float getPressure() {
        return pressure;
    }

    public Integer getPressureInHectoPascals() {
        if( pressure == null ) return null;

        // convert to hPa
        float val = pressure *33.8639F;
        // round
        return Math.round(val);
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperaturePrecise() {
        return temperaturePrecise;
    }

    public void setTemperaturePrecise(Float temperaturePrecise) {
        this.temperaturePrecise = temperaturePrecise;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Float getDewPointPrecise() {
        return dewPointPrecise;
    }

    public void setDewPointPrecise(Float dewPointPrecise) {
        this.dewPointPrecise = dewPointPrecise;
    }


    protected void setWindSpeedInMPS(Float value) {
        this.windSpeed = new Float(value.floatValue() / 0.5148);
    }

    /**
     *
     * @return wind speed in meters per second
     */
    public Float getWindSpeedInMPS() {
        return new Float(this.windSpeed.floatValue() * 0.5148);
    }

    /**
     *
     * @return wind speed in knots
     */
    public Float getWindSpeedInKnots() {
        return this.windSpeed;
    }

    /**
     *
     * @return wind speed in MPH
     */
    public Float getWindSpeedInMPH() {
        if (this.windSpeed == null) {
            return null;
        }

        double f = this.windSpeed.floatValue() * 1.1508;

        // round to the nearest MPH
        f = Math.round(f);

        return new Float(f);
    }

    /**
     *
     * @param value wind gust speed in meters per second
     */
    protected void setWindGustsInMPS(Float value) {
        this.windGusts = new Float(value.floatValue() / 0.5148);
    }

    /**
     *
     * @return wind gust speed in meters per second
     */
    public Float getWindGustsInMPS() {
        return new Float(this.windGusts.floatValue() * 0.5148);
    }

    /**
     *
     * @return wind gust speed in knots
     */
    public Float getWindGustsInKnots() {
        return this.windGusts;
    }

    /**
     *
     * @return wind gust speed in MPH
     */
    public Float getWindGustsInMPH() {
        if (this.windGusts == null) {
            return null;
        }

        double f = this.windGusts.floatValue() * 1.1508;

        // round to the nearest MPH
        f = Math.round(f);

        return new Float(f);
    }

    public boolean isCumulonimbus() {
        return isCumulonimbus;
    }

    public void setCumulonimbus(boolean cumulonimbus) {
        isCumulonimbus = cumulonimbus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;

        if (isWindDirectionIsVariable() != weather.isWindDirectionIsVariable()) return false;
        if (isCavok() != weather.isCavok()) return false;
        if (isVisibilityLessThan() != weather.isVisibilityLessThan()) return false;
        if (getId() != null ? !getId().equals(weather.getId()) : weather.getId() != null) return false;
        if (getOriginalContent() != null ? !getOriginalContent().equals(weather.getOriginalContent()) : weather.getOriginalContent() != null)
            return false;
        if (getReportType() != weather.getReportType()) return false;
        if (getReportDate() != null ? !getReportDate().equals(weather.getReportDate()) : weather.getReportDate() != null)
            return false;
        if (getCreateDate() != null ? !getCreateDate().equals(weather.getCreateDate()) : weather.getCreateDate() != null)
            return false;
        if (getWeatherStation() != null ? !getWeatherStation().equals(weather.getWeatherStation()) : weather.getWeatherStation() != null)
            return false;
        if (getPhenomena() != null ? !getPhenomena().equals(weather.getPhenomena()) : weather.getPhenomena() != null)
            return false;
        if (getText() != null ? !getText().equals(weather.getText()) : weather.getText() != null) return false;
        if (getVisibility() != null ? !getVisibility().equals(weather.getVisibility()) : weather.getVisibility() != null)
            return false;
        if (getVerticalVisibility() != null ? !getVerticalVisibility().equals(weather.getVerticalVisibility()) : weather.getVerticalVisibility() != null)
            return false;
        if (getRvr() != null ? !getRvr().equals(weather.getRvr()) : weather.getRvr() != null) return false;
        if (getCloud() != null ? !getCloud().equals(weather.getCloud()) : weather.getCloud() != null) return false;
        if (getFileName() != null ? !getFileName().equals(weather.getFileName()) : weather.getFileName() != null)
            return false;
        if (getWindDirection() != null ? !getWindDirection().equals(weather.getWindDirection()) : weather.getWindDirection() != null)
            return false;
        if (getWindDirectionMin() != null ? !getWindDirectionMin().equals(weather.getWindDirectionMin()) : weather.getWindDirectionMin() != null)
            return false;
        return getWindDirectionMax() != null ? getWindDirectionMax().equals(weather.getWindDirectionMax()) : weather.getWindDirectionMax() == null && (getWindSpeed() != null ? getWindSpeed().equals(weather.getWindSpeed()) : weather.getWindSpeed() == null && (getWindGusts() != null ? getWindGusts().equals(weather.getWindGusts()) : weather.getWindGusts() == null && (getVisibilityMiles() != null ? getVisibilityMiles().equals(weather.getVisibilityMiles()) : weather.getVisibilityMiles() == null && (getVisibilityKilometers() != null ? getVisibilityKilometers().equals(weather.getVisibilityKilometers()) : weather.getVisibilityKilometers() == null && (getVisibilityMeters() != null ? getVisibilityMeters().equals(weather.getVisibilityMeters()) : weather.getVisibilityMeters() == null && (getPressure() != null ? getPressure().equals(weather.getPressure()) : weather.getPressure() == null && (getTemperature() != null ? getTemperature().equals(weather.getTemperature()) : weather.getTemperature() == null && (getTemperaturePrecise() != null ? getTemperaturePrecise().equals(weather.getTemperaturePrecise()) : weather.getTemperaturePrecise() == null && (getDewPoint() != null ? getDewPoint().equals(weather.getDewPoint()) : weather.getDewPoint() == null && (getDewPointPrecise() != null ? getDewPointPrecise().equals(weather.getDewPointPrecise()) : weather.getDewPointPrecise() == null))))))))));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getOriginalContent() != null ? getOriginalContent().hashCode() : 0);
        result = 31 * result + (getReportType() != null ? getReportType().hashCode() : 0);
        result = 31 * result + (getReportDate() != null ? getReportDate().hashCode() : 0);
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getWeatherStation() != null ? getWeatherStation().hashCode() : 0);
        result = 31 * result + (getPhenomena() != null ? getPhenomena().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        result = 31 * result + (getVisibility() != null ? getVisibility().hashCode() : 0);
        result = 31 * result + (getVerticalVisibility() != null ? getVerticalVisibility().hashCode() : 0);
        result = 31 * result + (getRvr() != null ? getRvr().hashCode() : 0);
        result = 31 * result + (getCloud() != null ? getCloud().hashCode() : 0);
        result = 31 * result + (getTemperature() != null ? getTemperature().hashCode() : 0);
        result = 31 * result + (getPressure() != null ? getPressure().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", originalContent='" + originalContent + '\'' +
                ", reportType=" + reportType +
                ", reportDate=" + reportDate +
                ", createDate=" + createDate +
                ", weatherStation=" + weatherStation +
                ", phenomena=" + phenomena +
                ", text='" + text + '\'' +
                ", visibility='" + visibility + '\'' +
                ", verticalVisibility='" + verticalVisibility + '\'' +
                ", rvr='" + rvr + '\'' +
                ", cloud='" + cloud + '\'' +
                ", temperature='" + temperature + '\'' +
                ", pressure='" + pressure + '\'' +
                '}';
    }

    public enum ReportType {
        METAR,
        TAFOR,
        SPECI,
        SYNOP,
        AIRMET
    }
}
