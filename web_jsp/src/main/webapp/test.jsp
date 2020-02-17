<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Trial</title>
    <script src="js/jquery-3.1.1.js"></script>
    <script src="js/script.js"></script>
</head>

<body>

    name : <label for="nameid"></label><input type="text" name="name" id="nameid"><br>
    pwd : <label for="pwdid"></label><input type="text" name="pwd" id="pwdid"><br>
    <input type="button" value="submit" onclick="reg()">

    <div id="res"></div>
</body>

</html>