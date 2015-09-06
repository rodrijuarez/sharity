package com.rjuarez.webapp.controller;

import com.rjuarez.core.model.MovieResultsPage;

public interface IMovieController {
    MovieResultsPage getMovies(String query);
}
