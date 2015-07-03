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
package com.ruihu.easyshop.user.domain;

/**
 *
 * @author Rui Hu
 * 1 get t_user attribute from database package into User object
 */
public class User {
    private String uid;
    private String loginname;
    private String loginpass;
    private String email;
    private boolean  status;
    private String activationCode;
    //regist form
    private String reloginpass;
    private String verifyCode;
    //change password
    private String newloginpass;
    @Override
    public String toString() {
        return "User{" + "uid=" + uid + ", loginname=" + loginname + ", loginpass=" + loginpass + ", email=" + email + ", status=" + status + ", activationCode=" + activationCode + ", reloginpass=" + reloginpass + ", verifyCode=" + verifyCode + ", newloginpass=" + newloginpass + '}';
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
    
    
    
    public String getUid() {
        return uid;
    }

    public String getLoginname() {
        return loginname;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public String getEmail() {
        return email;
    }
 
    public String getActivationCode() {
        return activationCode;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

    public void setEmail(String email) {
        this.email = email;
    }
 
    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
 
  
    public String getReloginpass() {
        return reloginpass;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public String getNewloginpass() {
        return newloginpass;
    }

    public void setReloginpass(String reloginpass) {
        this.reloginpass = reloginpass;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setNewloginpass(String newloginpass) {
        this.newloginpass = newloginpass;
    }
    
    
}
