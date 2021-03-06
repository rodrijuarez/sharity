/*
 *      Copyright (c) 2004-2015 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.rjuarez.webapp.moviedbapi;

import java.net.URL;

import org.yamj.api.common.exception.ApiException;
import org.yamj.api.common.exception.ApiExceptionType;

public class MovieDbException extends ApiException {

    private static final long serialVersionUID = 1L;

    public MovieDbException(final ApiExceptionType exceptionType, final String response) {
        super(exceptionType, response);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final URL url) {
        super(exceptionType, response, url);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final int responseCode, final URL url) {
        super(exceptionType, response, responseCode, url);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final String url) {
        super(exceptionType, response, url);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final int responseCode, final String url) {
        super(exceptionType, response, responseCode, url);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final URL url, final Throwable cause) {
        super(exceptionType, response, url, cause);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final int responseCode, final URL url,
            final Throwable cause) {
        super(exceptionType, response, responseCode, url, cause);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final String url, final Throwable cause) {
        super(exceptionType, response, url, cause);
    }

    public MovieDbException(final ApiExceptionType exceptionType, final String response, final int responseCode, final String url,
            final Throwable cause) {
        super(exceptionType, response, responseCode, url, cause);
    }
}
