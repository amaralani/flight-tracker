package ir.mfava.modfava.pardazesh.model;

import javax.persistence.*;

public class AuditListener {

    @PostPersist
    @PostUpdate
    private void afterAddOrEdit(Object object) {

    }

    @PostRemove
    private void afterDelete(Object object){

    }
}
