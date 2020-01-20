package mine.learn.entity;

/**
 * LoginInf
 */
public class LoginInf {

    private int id;
    private String uname;
    private String upwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "LoginInf [id=" + id + ", uname=" + uname + ", upwd=" + upwd + "]";
    }

    public LoginInf(int id, String uname, String upwd) {
        this.id = id;
        this.uname = uname;
        this.upwd = upwd;
    }

    public LoginInf() {

    }
}