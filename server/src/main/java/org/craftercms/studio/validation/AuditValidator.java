package org.craftercms.studio.validation;


import org.craftercms.studio.commons.dto.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates that {@link org.craftercms.studio.commons.dto.Activity}
 * that is send by  a valid before passing it
 * to the business logic.
 */
public class AuditValidator implements Validator {
    private Logger log = LoggerFactory.getLogger(AuditValidator.class);

    public AuditValidator() {
    }

    /**
     * @see Validator#supports(Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return Activity.class.equals(clazz);
    }

    /**
     * @see Validator#validate(Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        if ( target instanceof Activity ) {
            if ( target != null ) {
                ValidatorUtils.validateStringNotEmpty(target, errors, "siteId", "siteName", "target", "type");
            }
        } else {
            log.debug("Given object is not a Activity instance not validating");
        }
    }
}
