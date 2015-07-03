/*
 * Copyright (C) 2015 Rui Hu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ruihu.easyshop.user.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*;
import org.junit.Test;
/**
 *
 * @author Rui Hu
 */
public class UserServiceTest {
    
    public UserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of ajaxValidateLoginname method, of class UserService.
     */
    @Test
    public void testAjaxValidateLoginname() {
        System.out.println("ajaxValidateLoginname");
        String loginname = "";
        UserService instance = new UserService();
        boolean expResult = false;
        boolean result = instance.ajaxValidateLoginname(loginname);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
     //   fail("The test case is a prototype.");
    }

    /**
     * Test of ajaxValidateEmail method, of class UserService.
     */
    @Test
    public void testAjaxValidateEmail() {
        System.out.println("ajaxValidateEmail");
        String email = "";
        UserService instance = new UserService();
        boolean expResult = false;
        boolean result = instance.ajaxValidateEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of regist method, of class UserService.
     */
    @Test
    public void testRegist() {
       
       UserService n=new UserService();
      n.regist(null);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("The test case is a prototype.");
    }
    
  
          
    
}
