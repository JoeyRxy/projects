<%@ page import="mine.learn.entity.LoginInf" %>
<%@ page import="java.util.List" %>
<%@ page import="mine.learn.service_let.All" %>
<%@ page import="mine.learn.dao.LogInDAO" %>
<%@ page import="java.sql.SQLException" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>

<body>
    <a href="<%=request.getContextPath() %>/all">query</a>
    <table border="1">
        <tr>
            <th>name</th>
            <th>pwd</th>
            <th>mobile</th>
            <th>opt</th>
        </tr>

        <%
            List<LoginInf> users = (List<LoginInf>)request.getAttribute("users");
            if(users!=null)
            for (LoginInf user :
                        users) {
        %>
        <tr>
            <td><%=user.getUname()%></td>
            <td><%=user.getUpwd()%></td>
            <td><%=user.getUmobile()%></td>
            <td><a href="delete?uname=<%=user.getUname()%>">删除</a></td>
        </tr>
        <%
            }
        %>
    </table>
</body>

</html>