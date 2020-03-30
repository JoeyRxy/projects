package top.mine.website.entity;

import java.util.Objects;

/**
 * User
 */
public class User {

    private int id;
    private String name;
    private String alias;
    private String pwd;

    public User(int id, String name, String alias, String pwd) {
        this.id = id;
        this.name = name;
        this.alias = alias;
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User [alias=" + alias + ", id=" + id + ", name=" + name + ", pwd=" + pwd + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
        return Objects.equals(name, other.name);
    }

    public User(String name, String alias, String pwd) {
        this.name = name;
        this.alias = alias;
        this.pwd = pwd;
    }

}