package com.rjuarez.webapp.controller.impl;

import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

import com.rjuarez.core.model.MovieResultsPage;
import com.rjuarez.webapp.controller.IMovieController;
import com.rjuarez.webapp.tools.ApiUrl;
import com.rjuarez.webapp.tools.HttpTools;
import com.rjuarez.webapp.tools.MethodSub;
import com.rjuarez.webapp.tools.TheMovieDatabaseMethod;
import com.rjuarez.webapp.tools.TheMovieDatabaseParameters;
import com.rjuarez.webapp.tools.TheMovieDatabaseQueries;

@Controller
@RequestMapping("/movie*")
public class MovieController implements IMovieController {

    private static final String API_KEY = "api.key";
    protected static final ObjectMapper MAPPER = new ObjectMapper();

    private MessageSourceAccessor messages;

    @Autowired
    public void setMessages(final MessageSource apiSource) {
        messages = new MessageSourceAccessor(apiSource);
    }

    // The HttpTools to use
    protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

    @Override
    @ResponseBody
    @RequestMapping(value = "/search*", method = RequestMethod.GET)
    public MovieResultsPage getMovies(@RequestParam(required = true, value = "q") final String query) {
        final TheMovieDatabaseParameters parameters = new TheMovieDatabaseParameters();
        parameters.add(TheMovieDatabaseQueries.QUERY, query);

        final URL url = new ApiUrl(messages.getMessage(API_KEY), TheMovieDatabaseMethod.SEARCH).subMethod(MethodSub.MOVIE).buildUrl(parameters);
        final RestTemplate restTemplate = new RestTemplate();
        final MovieResultsPage movies = restTemplate.getForObject(url.toString(), MovieResultsPage.class);
        return movies;
    }
}
