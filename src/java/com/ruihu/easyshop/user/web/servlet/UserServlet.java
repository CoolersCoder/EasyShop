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
package com.ruihu.easyshop.user.web.servlet;

import com.ruihu.easyshop.user.domain.User;
import com.ruihu.easyshop.user.service.UserService;
import com.ruihu.easyshop.user.service.exception.UserException;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ruihu.commons.CommonUtils;
import ruihu.servlet.BaseServlet;

/**
 *
 * @author Rui Hu
 */
public class UserServlet extends BaseServlet {
   private UserService userService= new UserService();
   /**
    * Regist function
    * @param req
    * @param resp
    * @throws ServletException
    * @throws IOException 
    */
    public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        
        User formUser=CommonUtils.toBean(req.getParameterMap(), User.class);
        
        
        
        Map<String,String> errors=validateRegist(formUser, req.getSession());
        if(errors.size()>0)
        {
            req.setAttribute("form", formUser);
            req.setAttribute("errors", errors);
            return "f:/jsps/user/regist.jsp";
        }
  
        
        
        userService.regist(formUser);
        req.setAttribute("code", "success");
        req.setAttribute("msg", "regist success, please verify your email!");
        return "f:/jsps/msg.jsp";
        
    }
   
    
    /**
     * verify items one by one! reu
     * @param formUser
     * @param session
     * @return 
     */
    private Map<String,String> validateRegist(User formUser, HttpSession session)
    {
        Map<String,String> errors=new HashMap<String, String>();
        /**
         * verify loginname
         */
        String loginname=formUser.getLoginname();
        if(loginname==null||loginname.trim().isEmpty())
        {
            errors.put("loginname", "username can not be null!");
        }else if(loginname.length()<3||loginname.length()>20)
        {
            errors.put("loginname","username should be between 3 and 20");
        }else if(!userService.ajaxValidateLoginname(loginname))
        {
            errors.put("loginname", "user has registed ready");
        }
        /**
         * 2 verify password
         */
         String loginpass=formUser.getLoginpass();
        if(loginpass==null||loginpass.trim().isEmpty())
        {
            errors.put("loginpass", "loginpass can not be null!");
        }else if(loginpass.length()<3||loginpass.length()>20)
        {
            errors.put("loginpass","loginpass should be between 3 and 20");
        }
        
        /**
         * 3 verify reloginpass
         */
         String reloginpass=formUser.getReloginpass();
        if(reloginpass==null||reloginpass.trim().isEmpty())
        {
            errors.put("reloginpass", "reloginpass can not be null!");
        }else if(!reloginpass.equals(loginpass))
        {
            errors.put("reloginpass","password is not same");
        }
        /*
          verify email
        */
        String email=formUser.getEmail();
        if(email==null||email.trim().isEmpty())
        {
            errors.put("email", "email can not be null!");
        }else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$"))
        {
            errors.put("email","email formate is wrong!");
        }else if(!userService.ajaxValidateEmail(email))
        {
            errors.put("email", "email has registed ready");
        }
        /*
        5 verify verifycode
        */
           String verifyCode=formUser.getVerifyCode();
           String vcode=(String) session.getAttribute("vCode");
        if(verifyCode==null||verifyCode.trim().isEmpty())
        {
            errors.put("verifyCode", "verifyCode can not be null!");
        }else if(!verifyCode.equals(vcode))
        {
            errors.put("verifyCode","verifyCode is not same");
        }
        return errors;
        
    }
    /**
     * Using ajax to check user Loginname, email and code information from database;
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException 
     */
     	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		 
		String loginname = req.getParameter("loginname");
		 
		boolean b = userService.ajaxValidateLoginname(loginname);
		 
		resp.getWriter().print(b);
		return null;
	}
      
      public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  
        
        String email=req.getParameter("email");
        Boolean b = userService.ajaxValidateEmail(email);
        resp.getWriter().print(b);
        return null;
    } 
      public String updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   /**
    * package form into user object
    * get uid from seesion
    * using uid and oldpass from form to call service function
    * if exception, save information into request, refard pwd.jsp
    * sava sucess information into request
    * f to msg.jsp
    */
        User formUser= CommonUtils.toBean(req.getParameterMap(), User.class);
        User user=(User) req.getSession().getAttribute("sessionUser");
        if(user==null)
        {
            req.setAttribute("msg", "you have not login!");
            return "f:/jsps/user/login.jsp";
        }
       try {
           userService.updatePassword(user.getUid(), formUser.getNewloginpass(), formUser.getLoginname());
           req.setAttribute("msg", "modify success");
           req.setAttribute("code", "success");
           return "f:/jsps/msg.jsp";
       } catch (UserException ex) {
           req.setAttribute("msg", ex.getMessage());
           req.setAttribute("user", formUser);
           return "f:/jsps/user/pwd.jsp";
       }
         
    } 
      public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     
        String verifyCode= req.getParameter("verifyCode");
        //get vcode from seesion
        String vcode= (String)req.getSession().getAttribute("vcode");
        
        boolean b= verifyCode.equalsIgnoreCase(vcode);
       
        resp.getWriter().print(b);
        return null;
    }
    
      public String activation(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              //get activationCode
              String code=req.getParameter("activationCode");
              req.setAttribute("code", "success");
              req.setAttribute("msg", "activiation success , please go back");
       try {
           userService.activationin(code);
       } catch (UserException ex) {
          req.setAttribute("msg", ex.getMessage());
          req.setAttribute("code", "error");//notify msg display Wrong
       }
              
              
              return "f:/jsps/msg.jsp";   
    }
      
      
    public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              //get activationCode
        //page form to user
        User form = CommonUtils.toBean(req.getParameterMap(), User.class);
        //verify code
        Map<String, String> errors = validateLogin(form, req);
        if (errors.size() > 0) {
            req.setAttribute("form", form);
            req.setAttribute("errors", errors);
            return "f:/jsps/user/login.jsp";
        }
        //call user service login function
      
        User user=userService.login(form);
        if(user == null)  {
            req.setAttribute("msg", "user or password is wrong");
            req.setAttribute("user", form);
            return "f:/jsps/user/login.jsp";
        }else{
            if(!user.isStatus())
            {
                req.setAttribute("msg", "You haven't active your account");
                req.setAttribute("user", form);
                return "f:/jsps/user/login.jsp";
            }else{
                //User from database;
                req.getSession().setAttribute("sessionUser", user);
                String loginname= user.getLoginname();
                loginname=URLEncoder.encode("loginname", "utf-8");
                Cookie cookie=new Cookie("loginname",loginname );
                cookie.setMaxAge(60*60*24*5);               //save 5 days
                resp.addCookie(cookie);
                return "r:/index.jsp";
            }
        }
  
    }
   
    public String quit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
        req.getSession().invalidate();
        return "r:/jsps/user/login.jsp";
  }
   
       private Map<String,String> validateLogin(User form, HttpServletRequest req)
    {
        Map<String,String> errors= new HashMap<String,String>();
        String loginname = form.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "username can not be null！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "username lengh should between 3 and 20");
		}
		
		/*
		 * 2. verify loginpass
		 */
		String loginpass = form.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "password can not be null！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "password lengh should between 3 and 20");
		}
		/**
                 * 3.verify verify code
                 */
		String verifyCode = form.getVerifyCode();
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "verify code can not be null！");
		} else if(verifyCode.length() != 4) {
			errors.put("verifyCode", "verify code lengh wrong!");
		} else {
			String sessionVerifyCode = (String)req.getSession().getAttribute("vCode");
			if(!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
				errors.put("verifyCode", "verify code wrong！");
			}
		}
        
        
        
        
        
        
        return errors;
    }
      
       
     
}
