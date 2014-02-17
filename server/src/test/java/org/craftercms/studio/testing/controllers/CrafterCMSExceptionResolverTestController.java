package org.craftercms.studio.testing.controllers;

import org.craftercms.studio.commons.exception.StudioException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/testing")
public class CrafterCMSExceptionResolverTestController {

    public static final String EXCEPTION_MSG ="This is Intended to Fail" ;

    @RequestMapping(value = "/throwUnregisterCrafterCMSException",method = RequestMethod.GET)
    public void throwUnregisterException()throws Exception{
         throw new StudioException(StudioException.ErrorCode.SYSTEM_ERROR);
    }

    @RequestMapping(value = "/throwUnregisterException",method = RequestMethod.GET)
    public void throwKnownException()throws Exception{
        throw new Exception(EXCEPTION_MSG);
    }

}
