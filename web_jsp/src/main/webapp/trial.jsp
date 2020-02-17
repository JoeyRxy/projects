<%@ page import="mine.learn.entity.UserInf" %>
<%@ page import="java.util.List" %>
<%@ page import="mine.learn.service_let.GetAll" %>
<%@ page import="mine.learn.dao.UserDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="mine.learn.entity.Page" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="js/jquery-3.1.1.js"></script>
    <script src="js/script.js"></script>
    <title>Document</title>
</head>

<body>
    <a href="page">query</a>
    <table border="1">
        <tr>
            <th>name</th>
            <th>pwd</th>
            <th>mobile</th>
            <th>opt</th>
        </tr>

        <%
//            response.sendRedirect("page");ATTENTION: 陷入死循环了……page页面每次导回这里，这里又到page，循环无尽了
            Page p = (Page)request.getAttribute("p");
            int tailPage = -1;
            if(p!=null){
            for (UserInf user : p.getUsers()) {
        %>
        <tr id="tr-d">
            <td><%=user.getUname()%></td>
            <td><%=user.getUpwd()%></td>
            <td><%=user.getUmobile()%></td>
            <td><a href="delete?uname=<%=user.getUname()%>">删除</a></td>
        </tr>
        <%
            }
            int count = 0;
            try {
                count = UserDAO.queryCount();
            } catch (SQLException e) {
                e.printStackTrace();
            }
             tailPage = ((count % p.getPageSize()) == 0) ? ((count / p.getPageSize()) - 1) : (count / p.getPageSize());
        %>
    </table>
    <%
        if(p.getCurPage()==0){
            %>
    <a href="page?curPage=<%=p.getCurPage()+1%>">下一页</a>
    <a href="page?curPage=<%=tailPage%>">尾页</a>
    <%=p.getCurPage()%>/<%=tailPage%>
    <%
        }else if(p.getCurPage()==tailPage){
    %>
    <a href="page?curPage=0">首页</a>
    <a href="page?curPage=<%=p.getCurPage()-1%>">上一页</a>
    <%=p.getCurPage()%>/<%=tailPage%>
    <%
    }    else {
    %>
    <a href="page?curPage=0">首页</a>
    <a href="page?curPage=<%=p.getCurPage()-1%>">上一页</a>
    <a href="page?curPage=<%=p.getCurPage()+1%>">下一页</a>
    <a href="page?curPage=<%=tailPage%>">尾页</a>
    <%=p.getCurPage()%>/<%=tailPage%>

    <%}%>
    <br>
    <div>每页显示
        <select>
            <option id="s-10">10</option>
            <option id="s-20">20</option>
            <option id="s-30">30</option>
        </select>
        条
    </div>
    <%}%>
</body>

</html>