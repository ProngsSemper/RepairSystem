<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        h2{
            text-align:center;
        }
    </style>
    <title>广金报修</title>
</head>
<body>
<h2>欢迎来到广金报修系统</h2>


<%--<form id="login-form" >--%>

<%--    <input type="button" id="ddd" value="暴躁老哥的按钮"><br>--%>
<%--    <input type="text" id="id"><br>--%>
<%--    <input type="text" id="password"><br>--%>

<%--</form>--%>
</body>
<script>
    window.onload=function () {
        window.location.href='/view/user/login.html';
    }
</script>


<%--<script>--%>


<%--    $(document).ready(function () {--%>
<%--        $('#ddd').click(--%>
<%--            function () {--%>

<%--                const uId = $('#id').val();--%>
<%--                const uPassword = $('#password').val();--%>

<%--                if (!uId) {--%>
<%--                    alert("请输入账号！");--%>
<%--                    return;--%>
<%--                }--%>
<%--                if (!uPassword) {--%>
<%--                    alert("请输入密码！");--%>
<%--                    return;--%>
<%--                }--%>


<%--                $.ajax({--%>
<%--                    type: "post",--%>
<%--                    url: "student/login",--%>

<%--                    dataType: "json",--%>
<%--                    data: JSON.stringify({'id':uId,'password':uPassword}),--%>
<%--                    success: function (ans) {--%>
<%--                        if(ans.code==200)--%>
<%--                        {--%>
<%--                            alert("登录成功");--%>
<%--                            window.setTimeout("window.location.href='welcome.jsp'", 1000);--%>
<%--                        }--%>
<%--                    }--%>
<%--                });--%>


<%--            }--%>
<%--        )--%>
<%--    })--%>
<%--</script>--%>


</html>
