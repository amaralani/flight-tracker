package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

@Entity
@Table(name = "phenomena")
public class Phenomena extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String abbreviation;

    private String description;

    private String englishDescription;

    private String icon;

    private String iconFile;

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

    public String getEnglishDescription() {
        return englishDescription;
    }

    public void setEnglishDescription(String englishDescription) {
        this.englishDescription = englishDescription;
    }

    public String getIconFile() {
        return iconFile;
    }

    public void setIconFile(String iconFile) {
        this.iconFile = iconFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phenomena)) return false;

        Phenomena phenomena = (Phenomena) o;

        if (getId() != null ? !getId().equals(phenomena.getId()) : phenomena.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(phenomena.getTitle()) : phenomena.getTitle() != null) return false;
        if (getAbbreviation() != null ? !getAbbreviation().equals(phenomena.getAbbreviation()) : phenomena.getAbbreviation() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(phenomena.getDescription()) : phenomena.getDescription() != null)
            return false;
        if (getIcon() != null ? !getIcon().equals(phenomena.getIcon()) : phenomena.getIcon() != null) return false;
        return getPriority() == phenomena.getPriority();

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
        return "Phenomena{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", priority=" + priority +
                '}';
    }

}
