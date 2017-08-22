package ir.mfava.modfava.pardazesh.service;

import java.util.List;

/**
 * @author Maralani
 *         7/19/2017
 */
public interface BaseService<S> {

    boolean exists(S entity);

    List<S> getAll();

    S getById(String id);

    S save(S entity);

    void remove(S entity);
}
