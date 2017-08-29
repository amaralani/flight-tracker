package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

@Entity
@Table(name = "weather_event")
public class Event extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String abbreviation;

    private String description;

    private String icon;

    private Integer priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        if (getId() != null ? !getId().equals(event.getId()) : event.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(event.getTitle()) : event.getTitle() != null) return false;
        if (getAbbreviation() != null ? !getAbbreviation().equals(event.getAbbreviation()) : event.getAbbreviation() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(event.getDescription()) : event.getDescription() != null)
            return false;
        if (getIcon() != null ? !getIcon().equals(event.getIcon()) : event.getIcon() != null) return false;
        return getPriority() == event.getPriority();

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getAbbreviation() != null ? getAbbreviation().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getIcon() != null ? getIcon().hashCode() : 0);
        result = 31 * result + (getPriority() != null ? getPriority().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", priority=" + priority +
                '}';
    }

}
