package mine.learn.javawebajax.entity;

import java.util.Objects;

import com.alibaba.fastjson.JSONObject;

/**
 * User
 */
public class User {
    private String name;
    private String pwd;
    private String mobile;
    private JSONObject service = null;
    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public JSONObject getService() {
        return service;
    }

    public void setService(JSONObject service) {
        this.service = service;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile, name, pwd);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(mobile, other.mobile) && Objects.equals(name, other.name)
                && Objects.equals(pwd, other.pwd);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public User(String name, String pwd, String mobile) {
        this.name = name;
        this.pwd = pwd;
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User [balance=" + balance + ", mobile=" + mobile + ", name=" + name + ", pwd=" + pwd + ", service="
                + service + "]";
    }

}