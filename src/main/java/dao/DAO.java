package dao;

import java.util.List;

public interface DAO <T> {
    T  save(T object);
    T find(Integer id);
    List<T> findAll();
    void update(T object);
    void delete(Integer id);
    List<T> findAll(String args);
}
