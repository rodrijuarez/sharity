package com.rjuarez.core.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.rjuarez.core.dao.GenericDao;

@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {
    @Resource
    private SessionFactory sessionFactory;

    protected Class<? extends E> type;

    /**
     * By defining this class as abstract, we prevent Spring from creating
     * instance of this class If not defined as abstract,
     * getClass().getGenericSuperClass() would return Object. There would be
     * exception because Object class does not hava constructor with parameters.
     */
    @SuppressWarnings("rawtypes")
    public GenericDaoImpl() {
        final Type t = getClass().getGenericSuperclass();
        final ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    protected Session currentSession() {
        Session session = this.sessionFactory.getCurrentSession();

        if (session == null) {
            session = this.sessionFactory.openSession();
        }

        return session;
    }

    @Override
    public void add(final E entity) {
        currentSession().save(entity);
    }
    
    @Override
    public void saveOrUpdate(final E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void update(final E entity) {
        currentSession().saveOrUpdate(entity);
    }

    @Override
    public void remove(final E entity) {
        currentSession().delete(entity);
    }

    @Override
    public E find(final K key) {
        return (E) currentSession().get(type, key);
    }

    @Override
    public List<E> getAll() {
        return currentSession().createCriteria(type).list();
    }
}
