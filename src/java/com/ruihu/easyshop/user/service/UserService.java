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

import com.ruihu.easyshop.user.dao.UserDao;
import com.ruihu.easyshop.user.domain.User;
import com.ruihu.easyshop.user.service.exception.UserException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Properties;

import jodd.mail.Email;
import jodd.mail.EmailMessage;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;
import jodd.mail.SmtpSslServer;
import jodd.util.MimeTypes;

import org.junit.Test;
import ruihu.commons.CommonUtils;


/**
 *
 * @author Rui Hu
 */
public class UserService {
    private UserDao userDao = new UserDao();
    /*
    activiation function
    */
    public void updatePassword(String uid, String newPass, String oldPass) throws UserException
    {
        try {
           boolean bool= userDao.findByUidAndPassword(uid, oldPass);
           if(!bool)
           {
               throw new UserException("old password is wrong");
           }
           
           userDao.updatePassword(uid, newPass);
           
        } catch (SQLException ex) {
           throw new RuntimeException(ex);
        }
    }
    public User login(User user) {
		try {
			return userDao.findByLoginnameAndPassword(user.getLoginname(), user.getLoginpass());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
   public void activationin(String code) throws UserException
   {
        try {
            
            User user=userDao.findByCode(code);
            if(user==null) throw new UserException("unvali activation code");
            if(user.isStatus()) throw new UserException("You have actived your account");
            userDao.updateStates(user.getUid(), true);//modify condition
                    
        } catch (SQLException ex) {
           throw new RuntimeException(ex);
        }
       
      
       
   }
    
    
    public boolean ajaxValidateLoginname(String loginname) {
		try {
			return userDao.ajaxValidateLoginname(loginname);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
    
    
     public boolean ajaxValidateEmail(String email) 
   {
        try {
            return  userDao.ajaxValidateEmail(email);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
   }
     
     @Test
    public void regist(User user) {
        /**
         * 1.set UUID into uid attribute
         */
        user.setUid(CommonUtils.uuid());
        user.setStatus(false);
        user.setActivationCode((CommonUtils.uuid() + CommonUtils.uuid()));
        try {
            userDao.add(user);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        //using properties file to record something information , so it is flexable to modify content
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //get emial-host server address, admin username , password , email title, and content from properties file.
        String host = prop.getProperty("host"); //server name;
        String name = prop.getProperty("username");//username
        String pass = prop.getProperty("password");//password
        String title= prop.getProperty("subject"); //email title
        String content=prop.getProperty("content"); //email content
        String fromEmail= prop.getProperty("from");
        /**
         * 2.Starting send email, this part using jodd jar file's email model 
         *   reference website: http://jodd.org/doc/email.html
         */
        Email email = new Email();
        
        email.from(fromEmail);    //it is my original send address
        email.to(user.getEmail()); //user email
        email.setSubject(title);  //title for mail

        EmailMessage textMessage = new EmailMessage(title, MimeTypes.MIME_TEXT_PLAIN);
        email.addMessage(textMessage);
       
        EmailMessage htmlMessage = new EmailMessage(
                "<html><META http-equiv=Content-Type content=\"text/html; charset=utf-8\">"
                + MessageFormat.format(prop.getProperty("content"), user.getActivationCode()),
                MimeTypes.MIME_TEXT_HTML);
        email.addMessage(htmlMessage);
        //pass information into host, name , pass
        SmtpServer smtpServer = SmtpSslServer.create(host)
                .authenticateWith(name, pass);
        /**
         * open a seesion to send email
         */
        SendMailSession session = smtpServer.createSession();
        session.open();
        session.sendMail(email);
        session.close();

    }

      
}
