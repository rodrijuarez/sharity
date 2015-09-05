package com.rjuarez.webapp.controller;

import java.net.URL;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

import com.rjuarez.webapp.tools.ApiUrl;
import com.rjuarez.webapp.tools.HttpTools;
import com.rjuarez.webapp.tools.MethodSub;
import com.rjuarez.webapp.tools.TheMovieDatabaseMethod;
import com.rjuarez.webapp.tools.TheMovieDatabaseParameters;
import com.rjuarez.webapp.tools.TheMovieDatabaseQueries;

@Controller
@RequestMapping("/movie*")
public class MovieController {

    private static final String API_KEY = "api.key";

    private MessageSourceAccessor messages;

    // The HttpTools to use
    protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

    @RequestMapping(value = "/searchq?q=*", method = RequestMethod.GET)
    public ModelAndView getMovies(@RequestParam(required = false, value = "q") final String query) throws Exception {
        final TheMovieDatabaseParameters parameters = new TheMovieDatabaseParameters();
        parameters.add(TheMovieDatabaseQueries.QUERY, "Fight club");

        final URL url = new ApiUrl(messages.getMessage(API_KEY), TheMovieDatabaseMethod.SEARCH).subMethod(MethodSub.MOVIE).buildUrl(parameters);
        final String webpage = httpTools.getRequest(url);
        return new ModelAndView("admin/userList");
    }
}
