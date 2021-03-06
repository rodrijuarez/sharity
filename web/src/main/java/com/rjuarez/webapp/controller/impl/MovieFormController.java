package com.rjuarez.webapp.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rjuarez.core.manager.MovieService;
import com.rjuarez.core.model.Movie;
import com.rjuarez.webapp.controller.IFormController;
import com.rjuarez.webapp.tools.HttpTools;

@Controller
@RequestMapping("/movie/form*")
public class MovieFormController implements IFormController {

    private static final String MOVIE_FORM = "movieForm";

    // The HttpTools to use
    protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

    @Autowired
    private MovieService movieService;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView newGet() {
        final ModelAndView mav = getMovieFormView();
        return mav;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public String saveMovie(final Movie movie) {
        movieService.save(movie);
        return "Yes";
    }

    private ModelAndView getMovieFormView() {
        return new ModelAndView(MOVIE_FORM);
    }
}
