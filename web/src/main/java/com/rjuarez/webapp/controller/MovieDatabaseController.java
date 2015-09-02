package com.rjuarez.webapp.controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import com.rjuarez.webapp.tools.TMDbMethod;
import com.rjuarez.webapp.tools.TMDbParameters;
import com.rjuarez.webapp.tools.TMDbQueries;

@Controller
@RequestMapping("/movie/database*")
public class MovieDatabaseController {

	private static final String API_KEY = "api.key";

	private MessageSourceAccessor messages;

	// The HttpTools to use
	protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

	@Autowired
	public void setMessages(MessageSource apiSource) {
		messages = new MessageSourceAccessor(apiSource);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView newGet(@RequestParam(required = false, value = "q") String query) throws Exception {
		TMDbParameters parameters = new TMDbParameters();
		parameters.add(TMDbQueries.QUERY, "Fight club");

		URL url = new ApiUrl(messages.getMessage(API_KEY), TMDbMethod.SEARCH).subMethod(MethodSub.MOVIE)
				.buildUrl(parameters);
		String webpage = httpTools.getRequest(url);
		return new ModelAndView("admin/userList");
	}
}
