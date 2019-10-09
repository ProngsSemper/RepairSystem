// document.write("<script type='text/javascript' src='Ajax.js'></script>"); 
// document.onload=function(){
//     var data=JSON.parse(getVerCode());
// }
// document.write("<script language=javascript src='cookie.js'></script>");
function bodyScale() {
    var devicewidth = document.documentElement.clientWidth;
    var scale = devicewidth / 1440;
    document.body.style.zoom = scale;
}

window.onload = window.onresize = function () {
    bodyScale();
};
var submit = document.getElementsByClassName("Submit")[0];
var Name = document.getElementsByClassName("stuId")[0];
var Password = document.getElementsByClassName("stuPassword")[0];
var checkCode = document.getElementsByClassName("checkInput")[0];
var remmber = document.getElementsByClassName('square')[0];

//添加 radio是为了区分用户
//添加验证码是为了给后台判断验证码

$(function () {
    $('.chose').eq(0).click(function () {
        $('input').eq(0).attr("name", "stuId");
        $('input').eq(1).attr("name", "stuPassWord");
    })
    $('.chose').eq(1).click(function () {
        $('input').eq(0).attr("name", "adminld");
        $('input').eq(1).attr("name", "adminPasswprd");
    })
    $('.chose').eq(2).click(function () {
        $('input').eq(0).attr("name", "wId");
        $('input').eq(1).attr("name", "wPassWord");
    })

    $(".codeImg").click(function () {
        this.src += "?";
    });
})
submit.onclick = function () {

    //lyr添加的,不要改变位置，放到最上面的是加载时刻获得的，需要点击的时候更新和获取值
    var radio = $("input[name='identity']:checked").val();
    var vcode = $('#myCode').val();

    $.ajax({
        type: "POST",
        url: "/user/login",
        dataType: "json",
        data: JSON.stringify({
            "id": Name.value,
            "password": Password.value,
            'radio': radio,
            'vcode': vcode

        }),
        success: function (data,status,jqXHR) {
            // var rel = JSON.parse(msg);
            var rel = data;
            if (rel.code === 200) {
                if (remmber.checked) {
                    if (Name.name === "stuId") {
                        addcookie(Name.name, Name.value, 7);
                        addcookie(Password.name, Password.value, 7);
                    } else {
                        addcookie(Name.name, Name.value, 1825);
                        addcookie(Password.name, Password.value, 1825);
                    }
                }
                refreshCode();
                // window.location.href = "www.baidu.com";
                var identity = jqXHR.getResponseHeader('identity');
                alert('登录成功');
                if(identity==='student')
                {
                    window.setTimeout("window.location.href='/welcome.html'", 1000);
                }else if(identity==='admin')
                {
                    window.setTimeout("window.location.href='/welcome.html'", 1000);
                }else if(identity==='worker')
                {
                    window.setTimeout("window.location.href='/welcome.html'", 1000);
                }else {}



            } else {
                alert("用户名或密码错误");
                refreshCode();
            }
        },
        error: function (xhr) {
            alert(xhr.status + "error");
        }

    })
    return false;
}

function getVerCode() {
    var result = "";
    $.ajax({
        url: '',
        type: "GET",
        dataType: "json",
        async: false,//关闭异步加载,这样只有加载完成才进行下一步
        success: function (data) {
            result = data.code;
        },
        error: function (xml) {
            alert(xml.status);
        }
    });
    return result;
}

//lyr引入的刷新验证码的function
function refreshCode() {
    //1.获取验证码图片对象
    var vcode = document.getElementById("vcode");

    //2.设置其src属性，加时间戳
    vcode.src = "/checkCode.png?time=" + new Date().getTime();
}
