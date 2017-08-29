package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

@Entity
@Table(name = "weather")
public class Weather extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn
    private WeatherStation weatherStation;

    @ManyToOne
    @JoinColumn
    private Event event;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WeatherStation getWeatherStation() {
        return weatherStation;
    }

    public void setWeatherStation(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Weather)) return false;

        Weather weather = (Weather) o;

        if (getId() != null ? !getId().equals(weather.getId()) : weather.getId() != null) return false;
        if (getWeatherStation() != null ? !getWeatherStation().equals(weather.getWeatherStation()) : weather.getWeatherStation() != null)
            return false;
        return getText() != null ? getText().equals(weather.getText()) : weather.getText() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getWeatherStation() != null ? getWeatherStation().hashCode() : 0);
        result = 31 * result + (getText() != null ? getText().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", weatherStation=" + weatherStation +
                ", text='" + text + '\'' +
                '}';
    }
}
