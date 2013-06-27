package org.craftercms.studio3.web.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Compound Validator.
 */
public class CompoundValidator implements Validator {

    /**
     * Validators List.
     */
    private List<Validator> validators = new ArrayList<Validator>();

    /**
     * Default Constructor.
     */
    public CompoundValidator() {
    }

    @Override
    public boolean supports(Class<?> aClass) {
        boolean supports = false;
        for (Validator v : this.validators) {
            if ( v.supports(aClass) ) {
                supports = true;
                break;
            }
        }
        return supports;
    }

    @Override
    public void validate(Object o, Errors errors) {
        for (Validator v : this.validators) {
            if ( v.supports(o.getClass()) ) {
                v.validate(o, errors);
            }
        }
    }

    public List<Validator> getValidators() {
        return this.validators;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }
}
