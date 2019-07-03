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
            url: "http://127.0.0.1:8080/upload",
            type: "POST",
            data: formData,
            processData: false, // 告诉jQuery不要去处理发送的数据
            contentType: false, // 告诉jQuery不要去设置Content-Type请求头
            success: function(result) {
                //前端对result进行处理
            }
        })
    }
}