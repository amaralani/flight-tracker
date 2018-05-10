package ir.mfava.modfava.pardazesh.model.DTO;

import java.io.Serializable;

public class AnalyticDTO implements Serializable{

    private String name;

    private Float percent;

    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}
