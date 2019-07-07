/**
 * 聊天室页面函数
 */

/**
 * 获取当前时间
 */

function get_time() {
    var time = new Date;
    return time.getFullYear() + "年" + time.getMonth() + "月" + time.getDate() + "日" + time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds();
}

/**
 *文件上传和图片上传
 */
function fileBtn() {
    document.getElementById('file').click()
}

function upload(file) {
    var img = document.getElementById('img')
    var formData = new FormData()
    var temp = file.files[0]
    if (temp) {
        formData.append('file', temp)
        $.ajax({
            url: "http://" + window.location.href.split('/')[2] + "/upload",
            type: "POST",
            data: formData,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function(result) {
                //前端对result进行处理
                //websocket.send("<li><span class=\"get-time\">" + get_time() + "</span><br /><span class=\"get-message\">上传了文件:</span><a href=" + result.url + " download>" + result.name + '</a></li > ');
                websocket.send("<span class=\"text\">上传了文件:</span><a href=" + result.url + " download class=\"text\">" + result.name + '</a> ')
                    //    不加download会报错，点击下载立即退出聊天室
            }
        })
    }
}

function imgBtn() {
    document.getElementById('img').click()
}

function up_img(file) {
    var img = document.getElementById('img')
    var formData = new FormData()
    var temp = file.files[0]
    if (temp) {
        formData.append('file', temp)
        $.ajax({
            url: "http://" + window.location.href.split('/')[2] + "/upload",
            type: "POST",
            data: formData,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false,
            success: function(result) {
                //前端对result进行处理
                //websocket.send("<li><span class=\"get-time\">" + get_time() + "</span><br /><span class=\"get-message\">上传了文件:</span><a href=" + result.url + " download>" + result.name + '</a></li > ');
                websocket.send("<img src=" + result.url + " class=\"chat_img\"/> ")
                    //    不加download会报错，点击下载立即退出聊天室
            }
        })
    }
}

