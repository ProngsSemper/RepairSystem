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
                var page = $(".page");
                var table = $(".repairItem");
                // $(".page").html("");
                $(".repairItem").html("");
                var data = msg.data;
                // console.log(data);
                var b = $('.page').children().length == 0;

                if (b) {
                    page.append('<span class="page-number cur">' + 1 + '</span>');
                    for (var i = 2; i <= msg.totalPage; i++) {
                        page.append('<span class="page-number">' + i + '</span>');
                    }
                }

                $(".repairItem").append('<tr class="row "><td class="col">学号</td><td class="col">地址</td><td class="col">内容</td><td class="col">操作</td></tr>')
                // alert(2);
                for (var i = 0; i < msg.size; i++) {

                    var line = data[i];


                    var row = $('<tr class="row"></tr>');
                    var table = $(".repairItem");

                    var colNumber = $('<td class="col">' + data[i].stuId + '</td>');
                    var colAdress = $('<td class="col">' + line.room + '</td>');

                    var colContant = $('<td class="col special">' + line.formMsg + '</td>');

                    var colOperate = $('<td class="col"><a href="javascript:;" class="deal">处理</a><a href="javascript:;" class="del">删除</a></td>')

                    //有bug,需要检查
                    table.append(row);

                    row.append(colNumber, colAdress, colContant, colOperate);
                    $(".row").eq(i+1).attr("formId", line.formId);
                }
            },
            error:function (xhr) {
                alert("服务器出了点异常")
            }


        });


    });




});

