package org.craftercms.studio3.web.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

public class CompoundValidator implements Validator {

    List<Validator> validators = new ArrayList<Validator>();
    @Override
    public boolean supports(Class<?> aClass) {
        boolean supports = false;
        for (Validator v : validators) {
            if (v.supports(aClass)) {
                supports = true;
                break;
            }
        }
        return supports;
    }

    @Override
    public void validate(Object o, Errors errors) {
        for (Validator v : validators) {
            if (v.supports(o.getClass())) {
                v.validate(o, errors);
            }
        }
    }

    public List<Validator> getValidators() { return validators; }
    public void setValidators(List<Validator> validators) { this.validators = validators; }
}
