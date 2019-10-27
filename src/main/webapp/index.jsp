<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.repairsys.util.net.CookieUtil" %>
<html>
<head>
    <style>
        h2 {
            text-align: center;
        }
    </style>
    <title>广金报修</title>
</head>
<body>
<h2>欢迎来到广金报修系统</h2>


</body>
<script>
    <%
    System.out.println(123);
    String adminToken = CookieUtil.getCookie("adminToken",request);
    String adminId = CookieUtil.getCookie("adminId",request);
    String wToken = CookieUtil.getCookie("wToken",request);
    String workerId = CookieUtil.getCookie("workerId",request);
    if ((adminToken!=null&&adminId!=null)||(wToken!=null&&workerId!=null)){
        if(adminId!=null&&(!adminId.equals("")))
        {
            request.setAttribute("adminId",adminId);
            request.setAttribute("adminToken",adminToken);
        }else if(!wToken.equals("")){
            request.setAttribute("wToken",wToken);
            request.setAttribute("workerId",workerId);

        }
        request.getRequestDispatcher("/user/login").forward(request, response);
        out.clear();
        out=pageContext.pushBody();
        return;
    }else {
    %>
    window.onload = function () {
        window.location.href = 'login.html';
    };
    <%
        }
    %>
</script>


</html>
