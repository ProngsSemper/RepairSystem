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
    <%
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("index.do").forward(request,response);
        out.clear();
        out = pageContext.pushBody();

    %>

</body>
<script>




</script>


</html>
