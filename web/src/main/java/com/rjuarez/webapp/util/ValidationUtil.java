package com.rjuarez.webapp.util;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;

/**
 * ValidationUtil Helper class for performing custom validations that aren't
 * already included in the core Commons Validator.
 *
 * <p>
 * <a href="ValidationUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class ValidationUtil {
    // ~ Methods
    // ================================================================

    /**
     * Validates that two fields match.
     * 
     * @param bean
     * @param va
     * @param field
     * @param errors
     */
    public static boolean validateTwoFields(final Object bean, final ValidatorAction va, final Field field, final Errors errors) {
        final String value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        final String secondProperty = field.getVarValue("secondProperty");
        final String value2 = ValidatorUtils.getValueAsString(bean, secondProperty);

        if (!GenericValidator.isBlankOrNull(value)) {
            try {
                if (!value.equals(value2)) {
                    FieldChecks.rejectValue(errors, field, va);
                    return false;
                }
            } catch (final Exception e) {
                FieldChecks.rejectValue(errors, field, va);
                return false;
            }
        }

        return true;
    }
}
