package org.craftercms.studio3.testing.controllers;

import org.craftercms.studio3.utils.exceptions.AbstractCrafterCMSException;
import org.craftercms.studio3.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/testing")
public class CrafterCMSExceptionResolverTestController extends BaseController{

    public static final String EXCEPTION_MSG ="This is Intended to Fail" ;

    @RequestMapping(value = "/throwUnregisterCrafterCMSException",method = RequestMethod.GET)
    public void throwUnregisterException()throws Exception{
         throw new TestException(EXCEPTION_MSG);
    }

    @RequestMapping(value = "/throwUnregisterException",method = RequestMethod.GET)
    public void throwKnownException()throws Exception{
        throw new Exception(EXCEPTION_MSG);
    }

    class TestException extends AbstractCrafterCMSException{
        TestException(final String message) {
            super(message);
        }
    }
}
