package com.rjuarez.core.manager;

import com.rjuarez.core.model.Movie;

public interface MovieService extends GenericService<Movie, Long> {
    void save(Movie movie);
}