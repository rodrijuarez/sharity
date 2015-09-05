package com.rjuarez.webapp.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import javax.servlet.jsp.jstl.core.Config;

import org.appfuse.Constants;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

public class LocaleFilterTest {
    private LocaleFilter filter;

    @Before
    public void setUp() throws Exception {
        filter = new LocaleFilter();
        filter.init(new MockFilterConfig());
    }

    @Test
    public void testSetLocaleInSessionWhenSessionIsNull() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("locale", "es");

        final MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, new MockFilterChain());

        // no session, should result in null
        assertNull(request.getSession().getAttribute(Constants.PREFERRED_LOCALE_KEY));
        // thread locale should always have it, regardless of session
        assertNotNull(LocaleContextHolder.getLocale());
    }

    @Test
    public void testSetLocaleInSessionWhenSessionNotNull() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("locale", "es");

        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setSession(new MockHttpSession(null));

        filter.doFilter(request, response, new MockFilterChain());

        // session not null, should result in not null
        final Locale locale = (Locale) request.getSession().getAttribute(Constants.PREFERRED_LOCALE_KEY);
        assertNotNull(locale);
        assertNotNull(LocaleContextHolder.getLocale());
        assertEquals(new Locale("es"), locale);
    }

    @Test
    public void testSetInvalidLocale() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("locale", "foo");

        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setSession(new MockHttpSession(null));

        filter.doFilter(request, response, new MockFilterChain());

        // a locale will get set regardless - there's no such thing as an
        // invalid one
        assertNotNull(request.getSession().getAttribute(Constants.PREFERRED_LOCALE_KEY));
    }

    @Test
    public void testJstlLocaleIsSet() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("locale", "es");

        final MockHttpServletResponse response = new MockHttpServletResponse();
        request.setSession(new MockHttpSession(null));

        filter.doFilter(request, response, new MockFilterChain());

        assertNotNull(Config.get(request.getSession(), Config.FMT_LOCALE));
    }

    @Test
    public void testLocaleAndCountry() throws Exception {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        request.addParameter("locale", "zh_TW");

        final MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, new MockFilterChain());

        // session not null, should result in not null
        final Locale locale = (Locale) request.getSession().getAttribute(Constants.PREFERRED_LOCALE_KEY);
        assertNotNull(locale);
        assertEquals(new Locale("zh", "TW"), locale);
    }
}
