function signInSuccess(name) {
    alert("登录成功！欢迎" + name);
}

function signInFailed() {
    alert("登陆失败！用户名不存在或密码错误");
}

$(document).ready(function () {
    $("#tr:odd").css("background-color", "lightgrey");
})

document.getElementById("s-10").onselect = function () {
    $.ajax({
        url: "page?pageSize=10",
        type: "POST",
    })
}
document.getElementById("s-20").onselect = function () {
    $.ajax({
        url: "page?pageSize=20",
        type: "POST",
    })
}
document.getElementById("s-30").onselect = function () {
    $.ajax({
        url: "page?pageSize=30",
        type: "POST",
    })
}

function register() {
    const xmlHttpRequest = new XMLHttpRequest();
    const name = $("#nameid").val();
    const pwd = $("#pwdid").val();
    console.log(name + " : " + pwd);
    xmlHttpRequest.open("post", "trial", true);
    xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xmlHttpRequest.send({
        name: name,
        pwd: pwd
    });
    xmlHttpRequest.onreadystatechange = function () {
        console.log("readystatechanged");
        console.log("ready state : " + xmlHttpRequest.readyState);
        console.log("status : " + xmlHttpRequest.status);
        if (xmlHttpRequest.readyState === XMLHttpRequest.DONE && xmlHttpRequest.status === 200) {
            console.log("success");

            let text = xmlHttpRequest.responseText;
            if (text === "true") {
                alert("welcome " + name);
            } else {
                alert("wrong!");
            }
        }
    }
}

function reg() {
    const name = $("#nameid").val();
    const pwd = $("#pwdid").val();
    console.log(name + " : " + pwd);

    $.ajax({
        url: "/web_jsp/trial",
        method: "post",
        dataType: "json",
        data: {
            "name": name,
            "pwd": pwd
        },
        success: (res) => {
            console.log(res);
            $("#res").text("ans :" + res.ans);
        }
    })
}

function check() {
    const name = $("#nameid").val();
    console.log(name);
    $.ajax({
        url: "name",
        data: {
            "name": name
        },
        method: "post",
        dataType: "json",
        success: function (res) {
            var ans = res.ans;
            console.log(ans);

            if (!ans) {
                $("#back").text("用户名已存在")
            }
        }
    })
}