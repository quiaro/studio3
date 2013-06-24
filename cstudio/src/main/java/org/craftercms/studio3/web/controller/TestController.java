/*
 * Copyright (C) 2007-2013 Crafter Software Corporation.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.craftercms.studio3.web.controller;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * Test Controller.
 */
@Controller
public class TestController {

    /**
     *
     */
    private Logger log= LoggerFactory.getLogger(TestController.class);
    /**
     * Hello World.
     */
    @RequestMapping(value="/", method = RequestMethod.GET)

    public final @ResponseBody
    TestObject hello(){
        return new TestObject("A", new Date(), new TestObject("Child", null, null));
    }

    /**
     * Hello World.
     */
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public final @ResponseBody List<TestObject> hello2(){
        List<TestObject> lst = new FastList<TestObject>();
        lst.add(new TestObject("A", new Date(), new TestObject("Child", null, null)));
        lst.add(new TestObject("B", new Date(), new TestObject("Child B", null, null)));
        lst.add(new TestObject("C", new Date(), new TestObject("Child C", new Date(), null)));
        lst.add(new TestObject("D", new Date(), new TestObject("Child D", null, null)));
        return lst;
    }

    /**
     * Hello World.
     */
    @RequestMapping(value="/get", method = RequestMethod.POST)
    public final  void hello3(@RequestBody TestObject testObj, BindingResult result) throws Exception {
        TestObjectValidator validator = new TestObjectValidator();
        validator.validate(testObj, result);
        if (result.hasErrors()) {
            throw new Exception();
        } else {
            // call service layer
        }
       log.info("I got This {}",testObj);
    }





    /**
     * Hello World.
     */
    @RequestMapping(value="/lots", method = RequestMethod.POST)
    public final @ResponseBody void hello4(@RequestBody List<TestObject> testObjs,@RequestParam(value = "other") String other){
        log.info("I got This {} with this {}",testObjs,other);
    }


    /**
     * Hello World.
     */
    @RequestMapping(value="/lots2", method = RequestMethod.POST)
    public final @ResponseBody void hello5(@RequestBody List<TestObject> testObjs,@RequestParam(value = "other") String other){
        log.info("I got This {} with this {}",testObjs,other);
    }

    /**
     * Hello World.
     */
    @RequestMapping(value="/lots3", method = RequestMethod.GET)
    public final @ResponseBody void hello6(){
        throw new IllegalStateException();
    }




}
