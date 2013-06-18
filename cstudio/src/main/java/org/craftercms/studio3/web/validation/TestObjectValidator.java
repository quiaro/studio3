package org.craftercms.studio3.web.validation;

import org.craftercms.studio3.web.TestObject;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TestObjectValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TestObject.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (supports(o.getClass())) {
            TestObject object = (TestObject)o;
            if (object.getName().isEmpty()) {
                errors.rejectValue("name", "Name is empty");
            }
        } else {
            errors.reject("ERROR", "Given object class not supported by validator");
        }
    }
}
