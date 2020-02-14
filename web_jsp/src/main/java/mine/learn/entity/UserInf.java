package mine.learn.entity;

import java.util.Objects;

/**
 * LoginInf
 */
public class UserInf {

    private String uname;
    private String upwd;
    private String umobile;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getUmobile() {
        return umobile;
    }

    public void setUmobile(String umobile) {
        this.umobile = umobile;
    }

    public UserInf(String uname, String upwd, String umobile) {
        this.uname = uname;
        this.upwd = upwd;
        this.umobile = umobile;
    }

    @Override
    public String toString() {
        return "LoginInf [ uname=" + uname + ", upwd=" + upwd + ", umobile=" + umobile + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(umobile, uname, upwd);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserInf other = (UserInf) obj;
        return Objects.equals(umobile, other.umobile) && Objects.equals(uname, other.uname)
                && Objects.equals(upwd, other.upwd);
    }

}