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

package com.rjuarez.webapp.tools;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Parameters for the TMDB API
 *
 * @author Stuart
 */
public class TheMovieDatabaseParameters {

    private final Map<TheMovieDatabaseQueries, String> parameters = new EnumMap<>(TheMovieDatabaseQueries.class);

    /**
     * Construct an empty set of parameters
     */
    public TheMovieDatabaseParameters() {
    }

    /**
     * Get the entry set of the parameters
     *
     * @return
     */
    public Set<Map.Entry<TheMovieDatabaseQueries, String>> getEntries() {
        return parameters.entrySet();
    }

    /**
     * Add an array parameter to the collection
     *
     * @param key
     *            Parameter to add
     * @param value
     *            The array value to use (will be converted into a comma
     *            separated list)
     */
    public void add(final TheMovieDatabaseQueries key, final String[] value) {
        if (value != null && value.length > 0) {
            parameters.put(key, toList(value));
        }
    }

    public void add(final TheMovieDatabaseQueries key, final String value) {
        if (StringUtils.isNotBlank(value)) {
            parameters.put(key, value);
        }
    }

    public void add(final TheMovieDatabaseQueries key, final Integer value) {
        if (value != null && value > 0) {
            parameters.put(key, String.valueOf(value));
        }
    }

    public void add(final TheMovieDatabaseQueries key, final Float value) {
        if (value != null && value > 0f) {
            parameters.put(key, String.valueOf(value));
        }
    }

    public void add(final TheMovieDatabaseQueries key, final Boolean value) {
        if (value != null) {
            parameters.put(key, String.valueOf(value));
        }
    }

    public boolean has(final TheMovieDatabaseQueries key) {
        return parameters.containsKey(key);
    }

    public Object get(final TheMovieDatabaseQueries key) {
        return parameters.get(key);
    }

    public void remove(final TheMovieDatabaseQueries key) {
        parameters.remove(key);
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    /**
     * Check to see if the collection has items
     *
     * @return
     */
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Append any optional parameters to the URL
     *
     * @param appendToResponse
     * @return
     */
    public String toList(final String[] appendToResponse) {
        StringBuilder sb = new StringBuilder();
        boolean first = Boolean.TRUE;
        for (String append : appendToResponse) {
            if (first) {
                first = Boolean.FALSE;
            } else {
                sb.append(",");
            }
            sb.append(append);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(parameters, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
