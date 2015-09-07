package com.rjuarez.core.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.rjuarez.core.model.Movie;

@Repository
public class MovieDaoImpl extends GenericDaoImpl<Movie, Long> implements MovieDao {

}
