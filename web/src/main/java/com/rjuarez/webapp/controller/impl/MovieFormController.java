package com.rjuarez.webapp.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

import com.rjuarez.webapp.controller.IFormController;
import com.rjuarez.webapp.tools.HttpTools;

@Controller
@RequestMapping("/movie/form*")
public class MovieFormController implements IFormController {

    private static final String MOVIE_FORM = "movieForm";

    // The HttpTools to use
    protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView newGet() {
        final ModelAndView mav = getMovieFormView();
        return mav;
    }

    private ModelAndView getMovieFormView() {
        return new ModelAndView(MOVIE_FORM);
    }
}
