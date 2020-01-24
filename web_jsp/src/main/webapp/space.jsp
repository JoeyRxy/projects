<%--
  Created by IntelliJ IDEA.
  User: Rxy
  Date: 2020/1/21
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>个人空间</title>
    <!-- Meta tag Keywords -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="UTF-8" />
    <meta name="keywords" content="" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script>
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <!-- //Meta tag Keywords -->

    <!-- Custom-Files -->
    <link rel="stylesheet" href="css/bootstrap.css">
    <!-- Bootstrap-Core-CSS -->
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
    <!-- Style-CSS -->
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <!-- Font-Awesome-Icons-CSS -->
    <!-- //Custom-Files -->

    <!-- Web-Fonts -->
    <link
            href="http://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&amp;subset=devanagari,latin-ext"
            rel="stylesheet">
    <!-- //Web-Fonts -->
</head>
<body>
<!-- main banner -->
<div class="main-top" id="home">
    <!-- header -->
    <header>
        <div class="container-fluid">
            <div class="header d-lg-flex justify-content-between align-items-center py-3 px-sm-3">
                <!-- logo -->
                <div id="logo">
                    <h1><a href="index.jsp"><span class="fa fa-linode mr-2"></span>Rxy</a></h1>
                </div>
                <!-- //logo -->
                <!-- nav -->
                <div class="nav_w3ls">
                    <nav>
                        <label for="drop" class="toggle">Menu</label>
                        <input type="checkbox" id="drop" />
                        <ul class="menu">
                            <li><a href="index.jsp" class="active">主页</a></li>
                            <li><a href="about.jsp">关于我们</a></li>
                            <li><a href="pricing.jsp">价格</a></li>
                            <li>
                                <!-- First Tier Drop Down -->
                                <label for="drop-2" class="toggle toogle-2">更多 <span class="fa fa-angle-down"
                                                                                     aria-hidden="true"></span>
                                </label>
                                <a href="#">更多 <span class="fa fa-angle-down" aria-hidden="true"></span></a>
                                <input type="checkbox" id="drop-2" />
                                <ul>
                                    <li><a href="faq.jsp" class="drop-text">疑问</a></li>
                                    <li><a href="about.jsp" class="drop-text">Why Choose Us?</a></li>
                                    <li><a href="about.jsp" class="drop-text">Our Team</a></li>
                                </ul>
                            </li>
                        </ul>
                    </nav>
                </div>
                <!-- //nav -->
                <div class="d-flex mt-lg-1 mt-sm-2 mt-3 justify-content-center">
                    <nav>
                        <ul>
                            <%
                                String name= (String) session.getAttribute("uname");
                                if(name!=null) {
                                    out.print("<li><a href=\"space.jsp\">"+name+"</a></li>\n");
                                }
                                else {
                                    out.print("<li><a href=\"signup.jsp\">注册</a></li>\n" +
                                            "<li><a href=\"signin.jsp\" class=\"drop-text\">登录</a></li>");
                                }
                            %>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </header>
    <!-- //header -->

    <!-- banner -->

    <!-- //banner -->
</div>
<!-- //main banner -->
</body>
</html>
