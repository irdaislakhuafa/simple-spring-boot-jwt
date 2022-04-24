package com.irdaislakhuafa.simplespringbootjwt.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<A, B> {
    public Optional<A> save(A entity);

    public Optional<A> update(A entity);

    public Optional<A> findById(String id);

    public boolean isExistsById(String id);

    public List<A> findAll();

    public A mapToEntity(B dto) throws Exception;

    public List<A> mapToEntities(List<B> dtos) throws Exception;

    public boolean deleteById(String id);
}
