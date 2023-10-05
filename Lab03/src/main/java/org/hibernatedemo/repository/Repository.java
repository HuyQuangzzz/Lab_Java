package org.hibernatedemo.repository;

import java.util.List;

public interface Repository<T, K> {
    K add(T item);

    T get(K id);

    List<T> getAll();

    boolean remove(K id);

    boolean update(T item);
}
