<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="text/html; charset=utf-8">
    <title>Failed</title>
</head>

<body>
    <h1>Failed!  </h1>
    <%=request.getParameter("uname")%> doesn't exist OR password is WRONG

<br>
<form action="index.jsp">
    <input type="submit" value="OK">
</form>
</body>

</html>