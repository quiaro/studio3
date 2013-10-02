package org.craftercms.studio.validation;

import org.craftercms.studio.commons.dto.LockHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LockHandleValidator implements Validator {
    private Logger log = LoggerFactory.getLogger(LockHandleValidator.class);

    @Override
    public boolean supports(final Class<?> clazz) {
        return LockHandle.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        if ( target instanceof LockHandle ) {
            if ( target != null ) {
                ValidatorUtils.validateStringNotEmpty(target, errors, "id");
            }
        } else {
            log.debug("Given object is not a LockHandle instance not validating");
        }
    }
}
