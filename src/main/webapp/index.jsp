<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/js/jquery-2.1.0.min.js"></script>

</head>
<body>
<h2>Hello World!</h2>


<form >

    <input type="button" id="ddd" value="暴躁老哥的按钮">
</form>
</body>



<script>


    $(document).ready(function () {
        $('#ddd').click(
            function () {
                alert("即将发送");

                $.ajax({
                    type: "post",
                    url: "student/login",
                    data: JSON.stringify({'stuId':123,'stuPassword':1}),
                    dataType: "json",
                    success: function (ans) {
                        alert("接收到消息");
                        alert(ans)
                    }
                });


            }
        )
    })
</script>


</html>
