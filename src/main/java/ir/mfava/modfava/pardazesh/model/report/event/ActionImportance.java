package ir.mfava.modfava.pardazesh.model.report.event;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "action_importance")
public class ActionImportance implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Event.ActionType actionType;

    private Event.Importance importance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event.ActionType getActionType() {
        return actionType;
    }

    public void setActionType(Event.ActionType actionType) {
        this.actionType = actionType;
    }

    public Event.Importance getImportance() {
        return importance;
    }

    public void setImportance(Event.Importance importance) {
        this.importance = importance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionImportance)) return false;

        ActionImportance that = (ActionImportance) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getActionType() != that.getActionType()) return false;
        return getImportance() == that.getImportance();

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getActionType() != null ? getActionType().hashCode() : 0);
        result = 31 * result + (getImportance() != null ? getImportance().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActionImportance{" +
                "id=" + id +
                ", actionType=" + actionType +
                ", importance=" + importance +
                '}';
    }
}
