var websocket = null;

function conectWebsocket() {

    //判断当前浏览器是否支持WebSocket 
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://" + window.location.href.split('/')[2] + "/websocket/" + username);
    } else {
        alert('Not support websocket')
    }


    //连接发生错误的回调方法  
    websocket.onerror = function() {
        setMessageInnerHTML("error");
    };

    //连接成功建立的回调方法  
    websocket.onopen = function(event) {

    }

    //接收到消息的回调方法  
    websocket.onmessage = function(event) {
        var list = $("#list>ul")
        list.empty();

        if (event.data.indexOf("=") != -1) {
            name_list = event.data.substring(1, event.data.length - 1).split(",");
            for (i = 0; i < name_list.length; i++) {
                list.append("<li>" + name_list[i].split("=")[1] + "</li>")
            }
        } else {
            message = event.data;
            message = JSON.parse(message);
            setMessageInnerHTML(message);

        }
    }

    //连接关闭的回调方法  
    websocket.onclose = function() {
        var chat = $("#chat-message>ul");
        chat.append("<li>" + "<p  class=\"time\"><span>" + get_time() + "</span></p>" + "<p> 关闭连接，您已从聊天室退出 </p></li>")
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。  
    window.onbeforeunload = function() {
        websocket.close();
        window.localStorage.removeItem("username");
    }
}




//将消息显示在网页上  
function setMessageInnerHTML(message) {
    var chat = $("#chat-message>ul");
    chat.append("<li>" + "<p  class=\"time\"><span>" + get_time() + "</span></p>" + "<p>" + message.sender + "</p>" + "<span class=\"text\">" + message.content + "</span>" + '</li>');

}

//关闭连接  
function closeWebSocket() {
    websocket.close();
    window.localStorage.removeItem("username");
}

//发送消息  
function send() {
    var message = $("#textarea").val();
    if (message == "") {
        alert("不能发送空消息");
        return;
    }
    websocket.send(message);
    $("#textarea").val("");
}
$(document).ready(conectWebsocket());