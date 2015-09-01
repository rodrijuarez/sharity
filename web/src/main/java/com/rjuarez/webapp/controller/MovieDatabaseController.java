package com.rjuarez.webapp.controller;

import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.yamj.api.common.http.SimpleHttpClientBuilder;

import com.rjuarez.webapp.tools.ApiUrl;
import com.rjuarez.webapp.tools.HttpTools;
import com.rjuarez.webapp.tools.MethodBase;
import com.rjuarez.webapp.tools.Param;
import com.rjuarez.webapp.tools.TmdbParameters;

@Controller
@RequestMapping("/movie/database*")
public class MovieDatabaseController {

	// The HttpTools to use
	protected final HttpTools httpTools = new HttpTools(new SimpleHttpClientBuilder().build());

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView newGet(@RequestParam(required = false, value = "q") String query) throws Exception {
		TmdbParameters parameters = new TmdbParameters();
		parameters.add(Param.ID, 2);
		parameters.add(Param.LANGUAGE, "");
		parameters.add(Param.APPEND, "");

		URL url = new ApiUrl("1f407ba422a1f12e1ffb00c6f250b794", MethodBase.MOVIE).buildUrl(parameters);
		String webpage = httpTools.getRequest(url);
		return new ModelAndView("admin/userList");
	}
}
