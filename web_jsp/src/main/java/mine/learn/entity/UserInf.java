package mine.learn.entity;

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

}