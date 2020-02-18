package mine.learn.vue.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

//ATTENTION :类名 对应 数据库中的表名；属性名 对应 数据库中的列名
@Entity
@Data
public class Users {

    @Id
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String pwd;

    private String mobile;
    private Integer balance;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", pwd=" + pwd + ", mobile=" + mobile + "balance=" + balance + "]";
    }

    public Users(String name, String pwd, String mobile) {
        this.name = name;
        this.pwd = pwd;
        this.mobile = mobile;
    }

    public Users() {

    }

}
