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
package com.ruihu.easyshop.user.dao;

import com.ruihu.easyshop.user.domain.User;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import ruihu.jdbc.TxQueryRunner;

/**
 *
 * @author Rui Hu
 */
public class UserDao {
    private QueryRunner qr= new TxQueryRunner();
    
    public User findByLoginnameAndPassword(String loginname, String loginpass) throws SQLException {
		String sql = "select * from t_user where loginname=? and loginpass=?";
		return qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
	}
	
    /**
     * check user list
     * @param loginname 
     */
        public boolean ajaxValidateLoginname(String loginname) throws SQLException {
		String sql = "select count(1) from t_user where loginname=?";
		Number number = (Number)qr.query(sql, new ScalarHandler(), loginname);
		return number.intValue() == 0;
	}
   /**
    * check email list
    * @param email
    * @return
    * @throws SQLException 
    */
    public boolean ajaxValidateEmail(String email) throws SQLException
   {
       String sql = "select count(1) from t_user where email=?";
       Number number= (Number)qr.query(sql, new ScalarHandler(),email);
       //check user list if it equal 0, return null
       return number.intValue()==0;
   }
    
    
    public void add(User user) throws SQLException
    {
        String sql="insert into t_user values(?,?,?,?,?,?)";
        Object [] params={user.getUid(),user.getLoginname(),user.getLoginname(),user.getEmail(),user.isStatus(),user.getActivationCode()};
        qr.update(sql,params);
    }
    
    /**
     * using code find user
     * @param code
     * @return 
     */
    public User findByCode(String code) throws SQLException
    {
        String sql="select *from t_user where activationCode=?";
        return qr.query(sql, new BeanHandler<User>(User.class),code);
    }
    
    public void updateStates(String uid, boolean status) throws SQLException
    {
        String sql="update t_user set status=? where uid=?";
        qr.update(sql,status,uid);
    }
    
    public boolean findByUidAndPassword(String uid, String password) throws SQLException
    {
        String sql="select count(1) from t_user where uid=? and loginpass=?";
        Number number=(Number)qr.query(sql, new ScalarHandler(),uid,password);
        return number.intValue()>0; //find result
        
    }
    
    public void updatePassword(String uid, String password) throws SQLException
    {
        String sql="update t_user set loginpass=? where uid=?";
        qr.update(sql,password,uid);
        
    }
}
