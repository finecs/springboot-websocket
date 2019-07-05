function signin() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == "") { alert("请输入用户名"); return 0; }
    if (password == "") { alert("请输入密码"); return 0; }
    $.post("http://" + window.location.href.split('/')[2] + "/register", {
        "username": username,
        "password": password
    }, function(result) {
        if (result.result == "success") {
            alert("注册成功");
            $("#username").val("");
            $("#password").val("");
        } else {
            alert("您输入的用户名太受欢迎了，换一个试试");
            $("#username").val("");
            $("#password").val("");
        }
    })
}

function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == "") { alert("请输入用户名"); return 0; }
    if (password == "") { alert("请输入密码"); return 0; }
    $.post("http://" + window.location.href.split('/')[2] + "/login", {
        "username": username,
        "password": password
    }, function(result) {
        if (result.result == "fail") {
            alert("用户名或密码不正确，请重新输入");
            $("#username").val("");
            $("#password").val("");
        } else {
            alert("登陆成功")
            window.localStorage.setItem("username", username);
            window.location.href = "http://" + window.location.href.split('/')[2] + "/chat.html";
        }
    })
}