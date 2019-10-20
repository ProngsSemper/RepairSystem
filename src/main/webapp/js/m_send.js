$(document).ready(function () {

    var typeObj = $("#queryType");
    var btn = $("#m_query");
    var textObj = $("#m_text");

    var type = typeObj.val();
    var url = null;
    var page = 10;
    var limit = 10;
    var wName = null;
    var wType = null;
    var formId = null;
    var stuName = null;
    var carry = null;
    btn.click(function () {


        alert(type);
        if(type==1)
        {
            //工人名字
            url = "/admin/wName";
            wName = textObj.val();
            carry = {
                "wName":wName,
                "page":page,
                "limit":limit
            };

        }else if(type==2)
        {
            //工种类型
            url = "/admin/type/form";
            wType = textObj.val();
            carry={
                "wType":wType,
                "page":page,
                "limit":limit
            }

        }else if(type==3)
        {
            //报修单id
            url = "/admin/formId";
            formId = textObj.val();
            carry={
                "formId":formId,
                "page":page,
                "limit":limit
            }


        }else if(type==4)
        {
            //学生名字
            url = "/admin/stuName";
            stuName = textObj.val();
            carry={
                "stuName":stuName,
                "page":page,
                "limit":limit
            }
        }

        $.ajax({
            type: "POST",
            url: url,
            dataType: "json",
            data: JSON.stringify({
                carry
            }),

            success: function (msg) {

            },
            error:function (xhr) {
                alert("服务器出了点异常")
            }


        });


    });




});

