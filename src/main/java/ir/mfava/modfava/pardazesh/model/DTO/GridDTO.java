package ir.mfava.modfava.pardazesh.model.DTO;

import ir.mfava.modfava.pardazesh.model.Weather;

import java.text.SimpleDateFormat;

public class GridDTO {
    private String stationId;
    private String farsiName;
    private String time;
    private String windDirection;
    private Float windSpeed;
    private String visibility;
    private Float temperature;
    private Float dewPoint;
    private Float pressure;
    private String metar;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getFarsiName() {
        return farsiName;
    }

    public void setFarsiName(String farsiName) {
        this.farsiName = farsiName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindSpeedInMPH() {
        if (this.windSpeed == null) {
            return null;
        }

        double f = this.windSpeed * 1.1508;

        // round to the nearest MPH
        f = Math.round(f);

        return String.valueOf((float) f) + " MPH";
    }

    public String getWindSpeedInMPS() {
        if (this.windSpeed != null) {
            return String.valueOf((float) (this.windSpeed * 0.5148)) + " MPS";
        } else {
            return null;
        }
    }

    public String getWindSpeedInKnots() {
        return String.valueOf(this.windSpeed) + " Knots";
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getTemperatureInFahrenheit() {
        if (this.temperature == null) {
            return null;
        }

        // round to the nearest 1/10th
        float f = (float) Math.round((this.temperature * 9 / 5 + 32) * 10) / 10;

        return f + " F";
    }

    public String getTemperatureInCelsius() {
        return temperature + " C";
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getDewPointInCelsius() {
        return dewPoint + " C";
    }

    public String getDewPointInFahrenheit() {
        if (this.dewPoint == null) {
            return null;
        }

        // round to the nearest 1/10th
        float f = (float) Math.round((this.dewPoint * 9 / 5 + 32) * 10) / 10;

        return String.valueOf(f) + " F";
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public String getPressureInInHg() {
        return pressure + " InHg";
    }

    public String getPressureInHectoPascals() {
        if (pressure == null) return null;

        // convert to hPa
        float val = pressure * 33.8639F;
        // round
        return Math.round(val) + " Hpas";
    }

    public String getMetar() {
        return metar;
    }

    public void setMetar(String metar) {
        this.metar = metar;
    }

    public void mapFromWeather(Weather weather) {
        this.stationId = weather.getWeatherStation().getStationId();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        this.time = simpleDateFormat.format(weather.getReportDate());
        this.windDirection = String.valueOf(weather.getWindDirection());
        this.windSpeed = weather.getWindSpeed();
        this.visibility = weather.getVisibility();
        this.temperature = weather.getTemperature();
        this.dewPoint = weather.getDewPoint();
        this.pressure = weather.getPressure();
        this.metar = weather.getOriginalContent();
    }

    public enum WindSpeedUnit {
        KNOTS,
        MPH,
        MPS
    }

    public enum VisibilityUnit {
        METER,
        KILOMETER,
        MILE,
        FOOT
    }

    public enum TemparatureUnit {
        CENTIGRADE,
        FAHRENHEIT
    }

    public enum PressureUnit {
        ATM,
        HPAS,
        INGH,
        MMGH
    }
}
