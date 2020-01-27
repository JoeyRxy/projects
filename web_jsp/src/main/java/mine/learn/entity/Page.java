package mine.learn.entity;

import java.util.List;

/**
 * Page
 */
public class Page {

    private int curPage;

    /**
     * 页面大小：每页数据条数
     * */
    private int pageSize;



    private List<UserInf> users;

    @Override
    public String toString() {
        return "Page{" +
                "curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", users=" + users +
                '}';
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<UserInf> getUsers() {
        return users;
    }

    public void setUsers(List<UserInf> users) {
        this.users = users;
    }

    public Page(int curPage, int pageSize, List<UserInf> users) {
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.users = users;
    }
}