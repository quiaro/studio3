package org.craftercms.studio.validation;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.validation.Errors;

/**
 *  Utility Class for Validators controllers
 *  contains reusable methods for Validators.
 */
public  final class ValidatorUtils {

    /**
     * Hides CTOR
     */
    private ValidatorUtils(){}

    public static void validateStringNotEmpty(final Object toValidate, Errors errors, String... fields){
        Logger log= LoggerFactory.getLogger(ValidatorUtils.class);
        log.debug("Validating fields {} of {} ", fields, toValidate.getClass().getName());
        final BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(toValidate);
        for(String field :fields){
            if( StringUtils.isEmpty((String) wrapper.getPropertyValue(field))){
                errors.reject(field, field+" is can't be empty");
            }
        }
    }
}
