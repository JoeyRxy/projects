function signInSuccess(name) {
    alert("登录成功！欢迎" + name);
}

function signInFailed() {
    alert("登陆失败！用户名不存在或密码错误");
}

$(document).ready(function () {
    $("#tr:odd").css("background-color", "lightgrey");
})

document.getElementById("s-10").onselect=function () {
    $.ajax({
        url:"page?pageSize=10",
        type: "POST",
    })
}
document.getElementById("s-20").onselect=function () {
    $.ajax({
        url:"page?pageSize=20",
        type: "POST",
    })
}
document.getElementById("s-30").onselect=function () {
    $.ajax({
        url:"page?pageSize=30",
        type: "POST",
    })
}