package ir.mfava.modfava.pardazesh.model.DTO;

public class WeatherDTO {

    private String latitude;
    private String longitude;
    private String contentHeader;
    private String content;
    private String markerType;
    private Integer zoomLevelMax;
    private Integer zoomLevelMin;
    private Boolean isCumulonimbus = false;
    private Float windSpeedInKnots;

    public WeatherDTO(){}
    public WeatherDTO(String latitude, String longitude, String contentHeader, String content, String markerType, Integer zoomLevelMax, Integer zoomLevelMin,Boolean isCumulonimbus, Float windSpeedInKnots) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.contentHeader = contentHeader;
        this.content = content;
        this.markerType = markerType;
        this.zoomLevelMax = zoomLevelMax;
        this.zoomLevelMin = zoomLevelMin;
        this.isCumulonimbus = isCumulonimbus;
        this.windSpeedInKnots = windSpeedInKnots;
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

    public String getContentHeader() {
        return contentHeader;
    }

    public void setContentHeader(String contentHeader) {
        this.contentHeader = contentHeader;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMarkerType() {
        return markerType;
    }

    public void setMarkerType(String markerType) {
        this.markerType = markerType;
    }

    public Integer getZoomLevelMax() {
        return zoomLevelMax;
    }

    public void setZoomLevelMax(Integer zoomLevelMax) {
        this.zoomLevelMax = zoomLevelMax;
    }

    public Integer getZoomLevelMin() {
        return zoomLevelMin;
    }

    public void setZoomLevelMin(Integer zoomLevelMin) {
        this.zoomLevelMin = zoomLevelMin;
    }

    public Boolean getIsCumulonimbus() {
        return isCumulonimbus;
    }

    public void setIsCumulonimbus(Boolean isCumulonimbus) {
        this.isCumulonimbus = isCumulonimbus;
    }

    public Float getWindSpeedInKnots() {
        return windSpeedInKnots;
    }

    public void setWindSpeedInKnots(Float windSpeedInKnots) {
        this.windSpeedInKnots = windSpeedInKnots;
    }
}
