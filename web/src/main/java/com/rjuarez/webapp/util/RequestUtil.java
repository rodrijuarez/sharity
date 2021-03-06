package com.rjuarez.webapp.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Convenience class for setting and retrieving cookies.
 */
public final class RequestUtil {
    private static final int DAYS_PER_MONTH = 30;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_DAY = 3600;
    private static final Log log = LogFactory.getLog(RequestUtil.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private RequestUtil() {
    }

    /**
     * Convenience method to set a cookie
     *
     * @param response
     *            the current response
     * @param name
     *            the name of the cookie
     * @param value
     *            the value of the cookie
     * @param path
     *            the path to set it on
     */
    public static void setCookie(final HttpServletResponse response, final String name, final String value, final String path) {
        if (log.isDebugEnabled()) {
            log.debug("Setting cookie '" + name + "' on path '" + path + "'");
        }

        final Cookie cookie = new Cookie(name, value);
        cookie.setSecure(false);
        cookie.setPath(path);
        cookie.setMaxAge(MINUTES_PER_DAY * HOURS_PER_DAY * DAYS_PER_MONTH); // 30
                                                                            // days

        response.addCookie(cookie);
    }

    /**
     * Convenience method to get a cookie by name
     *
     * @param request
     *            the current request
     * @param name
     *            the name of the cookie to find
     *
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie(final HttpServletRequest request, final String name) {
        final Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if (cookies == null) {
            return returnCookie;
        }

        for (final Cookie thisCookie : cookies) {
            if (thisCookie.getName().equals(name) && !"".equals(thisCookie.getValue())) {
                returnCookie = thisCookie;
                break;
            }
        }

        return returnCookie;
    }

    /**
     * Convenience method for deleting a cookie by name
     *
     * @param response
     *            the current web response
     * @param cookie
     *            the cookie to delete
     * @param path
     *            the path on which the cookie was set (i.e. /appfuse)
     */
    public static void deleteCookie(final HttpServletResponse response, final Cookie cookie, final String path) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(path);
            response.addCookie(cookie);
        }
    }

    /**
     * Convenience method to get the application's URL based on request
     * variables.
     * 
     * @param request
     *            the current request
     * @return URL to application
     */
    public static String getAppUrl(final HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        final StringBuffer url = new StringBuffer();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80; // Work around java.net.URL bug
        }
        final String scheme = request.getScheme();
        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if (("http".equals("scheme") && (port != 80)) || ("https".equals(scheme) && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(request.getContextPath());
        return url.toString();
    }
}
