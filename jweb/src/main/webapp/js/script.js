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

function isLogIn() {
    $.post("logstate", null, res => {

    })
}