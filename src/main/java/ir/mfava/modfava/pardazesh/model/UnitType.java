package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

/**
 * Created by maralani on 8/27/17.
 */
@Entity
@Table(name = "unit_type")
public class UnitType extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UnitType)) return false;

        UnitType unitType = (UnitType) o;

        return getId() != null ? getId().equals(unitType.getId()) : unitType.getId() == null && (getName() != null ? getName().equals(unitType.getName()) : unitType.getName() == null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UnitType{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }
}
