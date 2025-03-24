package ru.msu.cmc.webprak.model.dao;

public interface BaseDAO<SomeEntity> {
    void add(SomeEntity entity);
    void update(SomeEntity entity);
    void delete(SomeEntity entity);

    SomeEntity getById(Integer id);
}