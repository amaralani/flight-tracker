package ir.mfava.modfava.pardazesh.model.DTO;

import ir.mfava.modfava.pardazesh.model.Track;

public class TrackDTO extends WeatherDTO {

    private String altitude;
    private String code;

    public TrackDTO(){

    }
    public TrackDTO(Track track){
        this.setLatitude(String.valueOf(track.getLatitude()));
        this.setLongitude(String.valueOf(track.getLongitude()));
        this.setAltitude(String.valueOf(track.getAltitude()));
        this.setCode(String.valueOf(track.getCode()));
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
