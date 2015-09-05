package com.rjuarez.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

import com.rjuarez.webapp.tools.HttpTools;

@Controller
@RequestMapping("/movie/form*")
public class MovieFormController {

    private static final String MOVIE_FORM = "movieForm";

    // The HttpTools to use
    protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView newGet(@RequestParam(required = false, value = "q") final String query) throws Exception {
        final ModelAndView mav = getMovieFormView();
        return mav;
    }

    private ModelAndView getMovieFormView() {
        return new ModelAndView(MOVIE_FORM);
    }
}
