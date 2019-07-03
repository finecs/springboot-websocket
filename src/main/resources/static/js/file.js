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
                websocket.send("<li><span>上传了文件</span><br /><a href=" + result.url + " download>" + result.name + '</a></li > ');
            //    不加download会报错，点击下载立即退出聊天室
            }
        })
    }
}