<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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




<script>
    window.onload=function () {
        var str = window.location.href;
        if(str.indexOf("index")<0)
        {
            window.location.href = str+"index.jsp?a=1";
        }else{
            var tmp=null;
            tmp = str.replace("jsp","do?a=3&b=1");
            window.location.href = tmp;
        }
    }
</script>



</body>






</html>


