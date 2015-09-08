package com.rjuarez.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.rjuarez.core.manager.MailEngine;
import com.rjuarez.core.manager.UserManager;
import com.rjuarez.core.model.User;
import com.rjuarez.webapp.util.RequestUtil;

/**
 * Simple class to retrieve and send a password hint to users.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/passwordHint*")
public class PasswordHintController {
    private final Log log = LogFactory.getLog(PasswordHintController.class);
    private UserManager userManager;
    private MessageSource messageSource;
    protected MailEngine mailEngine;
    protected SimpleMailMessage message;

    @Autowired
    public void setUserManager(final UserManager userManager) {
        this.userManager = userManager;
    }

    @Autowired
    public void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setMailEngine(final MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(final SimpleMailMessage message) {
        this.message = message;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView handleRequest(final HttpServletRequest request) throws Exception {
        log.debug("entering 'handleRequest' method...");

        final String username = request.getParameter("username");
        final MessageSourceAccessor text = new MessageSourceAccessor(messageSource, request.getLocale());

        // ensure that the username has been sent
        if (username == null) {
            log.warn("Username not specified, notifying user that it's a required field.");
            request.setAttribute("error", text.getMessage("errors.required", text.getMessage("user.username")));
            return new ModelAndView("login");
        }

        log.debug("Processing Password Hint...");

        // look up the user's information
        try {
            final User user = userManager.getUserByUsername(username);

            final StringBuffer msg = new StringBuffer();
            msg.append("Your password hint is: ").append(user.getPasswordHint());
            msg.append("\n\nLogin at: ").append(RequestUtil.getAppUrl(request));

            message.setTo(user.getEmail());
            final String subject = '[' + text.getMessage("webapp.name") + "] " + text.getMessage("user.passwordHint");
            message.setSubject(subject);
            message.setText(msg.toString());
            mailEngine.send(message);

            saveMessage(request, text.getMessage("login.passwordHint.sent", new Object[] { username, user.getEmail() }));
        } catch (final UsernameNotFoundException e) {
            log.warn(e.getMessage());
            saveError(request, text.getMessage("login.passwordHint.error", new Object[] { username }));
        } catch (final MailException me) {
            log.warn(me.getMessage());
            saveError(request, me.getCause().getLocalizedMessage());
        }

        return new ModelAndView(new RedirectView(request.getContextPath()));
    }

    @SuppressWarnings("unchecked")
    public void saveError(final HttpServletRequest request, final String error) {
        List<String> errors = (List<String>) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList<String>();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }

    // this method is also in BaseForm Controller
    @SuppressWarnings("unchecked")
    public void saveMessage(final HttpServletRequest request, final String msg) {
        List<String> messages = (List<String>) request.getSession().getAttribute(BaseFormController.MESSAGES_KEY);
        if (messages == null) {
            messages = new ArrayList<String>();
        }
        messages.add(msg);
        request.getSession().setAttribute(BaseFormController.MESSAGES_KEY, messages);
    }
}
