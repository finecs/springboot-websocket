function get_time() {
    var time = new Date;
    return time.getFullYear() + "年" + time.getMonth() + "月" + time.getDate() + "日" + time.getHours() + "时" + time.getMinutes() + "分" + time.getSeconds() + "秒";
}