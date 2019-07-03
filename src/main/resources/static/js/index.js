function signin() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == "") { alert("请输入用户名"); return 0; }
    if (password == "") { alert("请输入密码"); return 0; }
    $.post("http://127.0.0.1:8080/register", {
        "username": username,
        "password": password
    }, function(result) {
        if (result.result == "success")
            alert("注册成功");
        else alert("您输入的用户名太受欢迎了，换一个试试");
    })
}

function login() {
    var username = $("#username").val();
    var password = $("#password").val();
    if (username == "") { alert("请输入用户名"); return 0; }
    if (password == "") { alert("请输入密码"); return 0; }
    $.post("http://127.0.0.1:8080/login", {
        "username": username,
        "password": password
    }, function(result) {
        if (result.result == "fail") {
            alert("用户名或密码不正确，请重新输入");
        } else {
            alert("登陆成功")
            window.location.href = 'http://127.0.0.1:8080/chat.html';
        }
    })
}

/**
 * 需要完善的功能，点击注册后表单内容置空
 * 如果登录失败，表单内容置空
 */