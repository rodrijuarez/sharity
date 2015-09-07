package com.rjuarez.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.rjuarez.core.dao.GenericDao;
import com.rjuarez.core.dao.hibernate.MovieDao;
import com.rjuarez.core.manager.MovieService;
import com.rjuarez.core.model.Movie;

@Service
public class MovieServiceImpl extends GenericServiceImpl<Movie, Long> implements MovieService {

    private MovieDao movieDao;

    public MovieServiceImpl() {

    }

    @Autowired
    public MovieServiceImpl(@Qualifier("movieDaoImpl") final GenericDao<Movie, Long> genericDao) {
        super(genericDao);
        this.movieDao = (MovieDao) genericDao;
    }

    @Override
    public void save(final Movie movie) {
        movieDao.add(movie);
    }
}
