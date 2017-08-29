package ir.mfava.modfava.pardazesh.model.DTO;

public class WeatherDTO {

    String latitude;
    String longitude;
    String contentHeader;
    String content;
    String markerType;
    Integer zoomLevelMax;
    Integer zoomLevelMin;

    public WeatherDTO(String latitude, String longitude, String contentHeader, String content, String markerType, Integer zoomLevelMax, Integer zoomLevelMin) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.contentHeader = contentHeader;
        this.content = content;
        this.markerType = markerType;
        this.zoomLevelMax = zoomLevelMax;
        this.zoomLevelMin = zoomLevelMin;
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
}
