var submit = document.getElementsByClassName("Submit")[0];
var Name = document.getElementsByClassName("stuId")[0];
var Password = document.getElementsByClassName("stuPassword")[0];
var checkCode = document.getElementsByClassName("checkInput")[0];
var remmber = document.getElementsByClassName('remmber')[0];
var cartoon = document.getElementsByClassName("cartoon")[0];
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
});


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

//新的js代码
function load() {
    var loadingMask = document.getElementById('loadingDiv');
    loadingMask.parentNode.removeChild(loadingMask);
}

$(document).ready(function () {
    // $('#login').click()
    // $("body").delegate("#login","click",function(){
    //     //给登录按钮添加节流
    //     throttle(submitted,3000,1)
    // })
    var load=document.getElementById("login");
    load.onclick=throttle(submitted,3000,1);
    function submitted() {

        //lyr添加的,不要改变位置，放到最上面的是加载时刻获得的，需要点击的时候更新和获取值
        var radio = $("input[name='identity']:checked").val();
        var vcode = $('#myCode').val();
        var flag;
        var judge=remmber.checked;
        if(judge){
            flag=1;
        }
        else{
            flag=0;
        }
        // document.write(_LoadingHtml);
        // window.setTimeout(load,3000);

        $.ajax({
            type: "POST",
            url: "/user/login",
            dataType: "json",
            async: false,
            data: JSON.stringify({
                // "id": Name.value,
                // "password": Password.value,
                "id": $('#id').val(),
                "password": $('#password').val(),
                'radio': radio,
                'vcode': vcode,
                'flag':parseInt(flag)
            }),
            success: function (data, status, jqXHR) {
                // var rel = JSON.parse(msg);
                var rel = data;

                if (rel.code === 200) {

                    refreshCode();
                    var identity = jqXHR.getResponseHeader('identity');
                    cartoon.style.display = "block";
                    if (identity == 'student') {
                        window.setTimeout("window.location.href='/firstPage.html'", 1000);
                    } else if (identity == 'admin') {
                        window.setTimeout("window.location.href='/managerFirstPage.html'", 500);
                    } else if (identity == 'worker') {
                        window.setTimeout("window.location.href='/workerPage.html'", 1000);
                    }
                } else if(rel.code==403) {
                    alert("验证码错误");
                    refreshCode();
                }else{
                    alert("用户名或密码错误");
                    refreshCode();
                }
            },
            error: function (xhr) {
                alert(xhr.status + "error");
            }

        });
    }
        
});

