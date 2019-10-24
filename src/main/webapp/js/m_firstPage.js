
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

                var str = data.data[0].boardMsg;
                if(str==null)
                {
                    str = "";
                }
                $("#article").html(str+"");
                $('#date').html(data.data[0].date);

            }
        }
    })
});

