package com.rjuarez.manager.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.rjuarez.core.dao.GenericDao;
import com.rjuarez.core.manager.GenericService;

@Service
public abstract class GenericServiceImpl<E, K> implements GenericService<E, K> {

    private GenericDao<E, K> genericDao;

    public GenericServiceImpl(final GenericDao<E, K> genericDao) {
        this.genericDao = genericDao;
    }

    public GenericServiceImpl() {
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrUpdate(final E entity) {
        genericDao.saveOrUpdate(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<E> getAll() {
        return genericDao.getAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public E get(final K id) {
        return genericDao.find(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(final E entity) {
        genericDao.add(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(final E entity) {
        genericDao.update(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(final E entity) {
        genericDao.remove(entity);
    }
}