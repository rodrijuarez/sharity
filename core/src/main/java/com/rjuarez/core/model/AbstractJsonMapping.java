package com.rjuarez.core.model;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public abstract class AbstractJsonMapping implements Serializable {

    private static Logger getLogger(final Class<?> aClass) {
        return LoggerFactory.getLogger(aClass);
    }

    /**
     * Handle unknown properties and print a message
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(final String key, final Object value) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Unknown property: '").append(key);
        sb.append("' value: '").append(value).append("'");

        getLogger(this.getClass()).trace(sb.toString());
    }

}