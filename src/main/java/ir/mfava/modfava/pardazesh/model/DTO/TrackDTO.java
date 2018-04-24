package ir.mfava.modfava.pardazesh.model.DTO;

import ir.mfava.modfava.pardazesh.model.Track;
import ir.mfava.modfava.pardazesh.model.TrackLog;

public class TrackDTO extends WeatherDTO {

    private String altitude;
    private String code;
    private String heading;

    public TrackDTO(){

    }
    public TrackDTO(Track track){
        this.setLatitude(String.valueOf(track.getLatitude()));
        this.setLongitude(String.valueOf(track.getLongitude()));
        this.setAltitude(String.valueOf(track.getAltitude()));
        this.setCode(String.valueOf(track.getCode()));
        this.setHeading(String.valueOf(track.getHeading()));
    }
    public TrackDTO(TrackLog trackLog){
        this.setLatitude(String.valueOf(trackLog.getLatitude()));
        this.setLongitude(String.valueOf(trackLog.getLongitude()));
        this.setAltitude(String.valueOf(trackLog.getAltitude()));
        this.setCode(String.valueOf(trackLog.getCode()));
        this.setHeading(String.valueOf(trackLog.getHeading()));
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

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

}
