package org.craftercms.studio3.web.controller;

import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {

    protected Validator validator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    public Validator getValidator() { return validator; }
    public void setValidator(Validator validator) { this.validator = validator; }
}
