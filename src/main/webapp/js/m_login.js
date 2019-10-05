//林某人
$(document).ready(function () {
    $('#login').click(
        function () {

            var uId = $('#id').val();
            var uPassword = $('#password').val();
            var radio = $("input[name='1']:checked").val();
            var vcode = $('#myCode').val();

            if (!uId) {

                alert("请输入账号！");
                refreshCode();
                return;
            }
            if (!uPassword) {

                alert("请输入密码！");
                refreshCode();
                return;
            }
            if (!vcode) {
                alert("请填写验证码！");
                // alert("请填写验证码！");
                refreshCode();
                return;
            }
            alert('正在提交请稍候... ');


            $.ajax({
                type: "post",
                url: "/user/login",

                dataType: "json",
                data: JSON.stringify({'id':uId,'password':uPassword,'radio':radio,'vcode':vcode}),
                success: function (ans) {
                    if(ans.code==403)
                    {
                        alert('验证码错误');
                        refreshCode();
                    }
                    else if(ans.code==200)
                    {
                        alert('登录成功');
                        // alert("登录成功");
                        // refreshCode();
                        window.setTimeout("window.location.href='/welcome.jsp'", 1000);
                    }else {
                        alert('登录失败');
                        refreshCode();
                    }
                }
            });


        }
    )
})

function refreshCode(){
    //1.获取验证码图片对象
    var vcode = document.getElementById("vcode");

    //2.设置其src属性，加时间戳
    vcode.src = "/checkCode.png?time="+new Date().getTime();
}

