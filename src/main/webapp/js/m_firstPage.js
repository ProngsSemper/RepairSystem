
/*
*
* 该文件由 lyr编写
*
* */
$(document).ready(function () {
    $.ajax({
        type:"post",
        url:"/student/board",
        dataType:"json",
        success:function (data) {
            if(data.code==200)
            {

                // var str = data.data[0].boardMsg;
                // alert(str);
                // $("#article").html(str+"dddddddddddddd");
                // // console.log(str);
                // // alert(data.code);
                // // alert(str);
                // // alert(data.data.date);
                var str = data.data[0].boardMsg;
                if(str==null)
                {
                    str = "";
                }
                $("#article").html(str+"");

            }
        }
    })
});


// $.ajax({
//     type: "post",
//     url: "/user/login",
//
//     dataType: "json",
//     data: JSON.stringify({'id': uId, 'password': uPassword, 'radio': radio, 'vcode': vcode}),
//     success: function (ans) {
//         if (ans.code == 403) {
//             alert('验证码错误');
//             refreshCode();
//         } else if (ans.code == 200) {
//             alert('登录成功');
//             // alert("登录成功");
//             // refreshCode(); 刷新验证码
//             window.setTimeout("window.location.href='/welcome.jsp'", 1000);
//         } else {
//             alert('登录失败');
//             refreshCode();
//         }
//     }
// });
//
