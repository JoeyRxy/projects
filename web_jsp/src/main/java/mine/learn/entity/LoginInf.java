package mine.learn.entity;

/**
 * LoginInf
 */
public class LoginInf {

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

    public LoginInf(String uname, String upwd, String umobile) {
        this.uname = uname;
        this.upwd = upwd;
        this.umobile = umobile;
    }

    public LoginInf() {
    }

}