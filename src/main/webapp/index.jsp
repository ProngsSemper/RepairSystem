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

  window.onload=function () {
      var str = window.location.href;
      alert(str);
      if(str.indexOf("index.jsp")<0)
      {
          window.location.href=str+"/localhost/index.jsp";

      }else{
          <%

            request.getRequestDispatcher("index.do").forward(request,response);

          %>
      }
  }




</script>


</html>


