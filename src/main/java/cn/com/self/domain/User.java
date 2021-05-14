package cn.com.self.domain;

import java.util.Date;
import javax.persistence.*;

public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private String userId;

    private String name;

    private String password;

    private String gender;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "user_group")
    private String userGroup;

    @Column(name = "register_time")
    private Date registerTime;

    @Column(name = "login_flag")
    private String loginFlag;

    @Column(name = "last_login_time")
    private Date lastLoginTime;

    private String token;

    @Column(name = "token_exptime")
    private Date tokenExptime;

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return phone_num
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @param phoneNum
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @return user_group
     */
    public String getUserGroup() {
        return userGroup;
    }

    /**
     * @param userGroup
     */
    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @return register_time
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * @param registerTime
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * @return login_flag
     */
    public String getLoginFlag() {
        return loginFlag;
    }

    /**
     * @param loginFlag
     */
    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    /**
     * @return last_login_time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return token_exptime
     */
    public Date getTokenExptime() {
        return tokenExptime;
    }

    /**
     * @param tokenExptime
     */
    public void setTokenExptime(Date tokenExptime) {
        this.tokenExptime = tokenExptime;
    }
}