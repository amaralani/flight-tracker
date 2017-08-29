package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

@Entity
@Table(name = "unit")
public class Unit extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String code;

    @ManyToOne
    @JoinColumn
    private UnitType type;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UnitType getType() {
        return type;
    }

    public void setType(UnitType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Unit)) return false;

        Unit unit = (Unit) o;

        return getId() != null ? getId().equals(unit.getId()) : unit.getId() == null && (getTitle() != null ? getTitle().equals(unit.getTitle()) : unit.getTitle() == null && (getCode() != null ? getCode().equals(unit.getCode()) : unit.getCode() == null && (getType() != null ? getType().equals(unit.getType()) : unit.getType() == null)));

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getCode() != null ? getCode().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                '}';
    }
}
