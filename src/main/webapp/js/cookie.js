function addcookie(key, value, day, path, domain) {
    //1.处理默认保存的路径
    var index = window.location.pathname.lastIndexOf("/");
    var currenPath = window.location.pathname.slice(0, index);
    path = currenPath || currenPath;
    //2.处理默认保存的domain
    domain = domain || document.domain;
    //3.处理默认过期时间
    if (!day) {
        document.cookie = key + "=" + value + ";path=" + path + ";domain=" + domain;
    } else {
        var date = new Date();
        date.setDate(date.getDate() + day);
        document.cookie = key + "=" + value + ";expires=" + date.toGMTString() + ";domain=" + domain;
    }

}

// var index=window.location.pathname.lastIndexOf("/");
// var currenPath=window.location.pathname.slice(0,index);
// path=currenPath;
function getCookie(key) {
    var res = document.cookie.split(";");
    for (var i = 0; i < res.length; i++) {
        var temp = res[i].split("=");
        if (temp[0].trim() == key) {
            return temp[1];
        }
    }
}