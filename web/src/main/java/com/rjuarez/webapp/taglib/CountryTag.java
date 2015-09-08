package com.rjuarez.webapp.taglib;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.displaytag.tags.el.ExpressionEvaluator;

import com.rjuarez.core.model.LabelValue;

/**
 * Tag for creating multiple &lt;select&gt; options for displaying a list of
 * country names.
 *
 * <p>
 * <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 * </p>
 *
 * @author Jens Fischer, Matt Raible
 * @version $Revision: 1.6 $ $Date: 2006/07/15 11:57:20 $
 *
 * @jsp.tag name="country" bodycontent="empty"
 */
public class CountryTag extends TagSupport {
    private static final long serialVersionUID = 3905528206810167095L;
    private String name;
    private String prompt;
    private String scope;
    private String selected;

    /**
     * @param name
     *            The name to set.
     *
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param prompt
     *            The prompt to set.
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setPrompt(final String prompt) {
        this.prompt = prompt;
    }

    /**
     * @param selected
     *            The selected option.
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setDefault(final String selected) {
        this.selected = selected;
    }

    /**
     * Property used to simply stuff the list of countries into a specified
     * scope.
     *
     * @param scope
     *
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setToScope(final String scope) {
        this.scope = scope;
    }

    /**
     * Process the start of this tag.
     *
     * @return int status
     *
     * @exception JspException
     *                if a JSP exception has occurred
     *
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        final ExpressionEvaluator eval = new ExpressionEvaluator(this, pageContext);

        if (selected != null) {
            selected = eval.evalString("default", selected);
        }

        final Locale userLocale = pageContext.getRequest().getLocale();
        final List<LabelValue> countries = this.buildCountryList(userLocale);

        if (scope != null) {
            if ("page".equals(scope)) {
                pageContext.setAttribute(name, countries);
            } else if ("request".equals(scope)) {
                pageContext.getRequest().setAttribute(name, countries);
            } else if ("session".equals(scope)) {
                pageContext.getSession().setAttribute(name, countries);
            } else if ("application".equals(scope)) {
                pageContext.getServletContext().setAttribute(name, countries);
            } else {
                throw new JspException("Attribute 'scope' must be: page, request, session or application");
            }
        } else {
            final StringBuilder sb = new StringBuilder();
            sb.append("<select name=\"").append(name).append("\" id=\"").append(name).append("\" class=\"form-control\">\n");

            if (prompt != null) {
                sb.append("    <option value=\"\" selected=\"selected\">");
                sb.append(eval.evalString("prompt", prompt)).append("</option>\n");
            }

            for (final Iterator<LabelValue> i = countries.iterator(); i.hasNext();) {
                final LabelValue country = i.next();
                sb.append("    <option value=\"").append(country.getValue()).append("\"");

                if ((selected != null) && selected.equals(country.getValue())) {
                    sb.append(" selected=\"selected\"");
                }

                sb.append(">").append(country.getLabel()).append("</option>\n");
            }

            sb.append("</select>");

            try {
                pageContext.getOut().write(sb.toString());
            } catch (final IOException io) {
                throw new JspException(io);
            }
        }

        return super.doStartTag();
    }

    /**
     * Release aquired resources to enable tag reusage.
     *
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    @Override
    public void release() {
        super.release();
    }

    /**
     * Build a List of LabelValues for all the available countries. Uses the two
     * letter uppercase ISO name of the country as the value and the localized
     * country name as the label.
     *
     * @param locale
     *            The Locale used to localize the country names.
     *
     * @return List of LabelValues for all available countries.
     */
    protected List<LabelValue> buildCountryList(final Locale locale) {
        final String empty = "";
        final Locale[] available = Locale.getAvailableLocales();

        final List<LabelValue> countries = new ArrayList<>();

        for (int i = 0; i < available.length; i++) {
            final String iso = available[i].getCountry();
            final String name = available[i].getDisplayCountry(locale);

            if (!empty.equals(iso) && !empty.equals(name)) {
                final LabelValue country = new LabelValue(name, iso);

                if (!countries.contains(country)) {
                    countries.add(new LabelValue(name, iso));
                }
            }
        }

        Collections.sort(countries, new LabelValueComparator(locale));

        return countries;
    }

    /**
     * Class to compare LabelValues using their labels with locale-sensitive
     * behaviour.
     */
    public class LabelValueComparator implements Comparator<Object> {
        private final Comparator<Object> comparator;

        /**
         * Creates a new LabelValueComparator object.
         *
         * @param locale
         *            The Locale used for localized String comparison.
         */
        public LabelValueComparator(final Locale locale) {
            comparator = Collator.getInstance(locale);
        }

        /**
         * Compares the localized labels of two LabelValues.
         *
         * @param o1
         *            The first LabelValue to compare.
         * @param o2
         *            The second LabelValue to compare.
         *
         * @return The value returned by comparing the localized labels.
         */
        @Override
        public final int compare(final Object o1, final Object o2) {
            final LabelValue lhs = (LabelValue) o1;
            final LabelValue rhs = (LabelValue) o2;

            return comparator.compare(lhs.getLabel(), rhs.getLabel());
        }
    }
}
