//上来先获取第一页数据，并生成页码
$(document).ready(function () {
    var bodyWidth = window.screen.width;
    var body = document.getElementsByTagName("body")[0];
    body.style.width = window.screen.width;
    var data = document.getElementsByClassName("date")[0];
    var a = new Date();
    var day = a.getDate();
    var month = a.getMonth() + 1;
    var year = a.getFullYear();
    var b = new Array("日", "一", "二", "三", "四", "五", "六");
    var week = new Date().getDay();
    var str = " 星期" + b[week];
    var str1 = year + '年' + month + '月' + day + '日';
    data.innerText = str1 + str;
    getMsg(1);
    // alert(1);
    // getCookie(stuId);
});

function getMsg(pageCount) {
    $.ajax({
        type: "POST",
        url: "/admin/incomplete/form",
        dataType: "json",
        data: JSON.stringify({
            "page": pageCount,
            "limit": 10,
            "adminId": "admin"
        }),
        success: function (msg) {

            // alert(msg.size);
            var page = $(".page");
            // $(".page").html("");
            $(".repairItem").html("");
            var data = msg.data;
            console.log(data);
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
                $(".row").eq(i).attr("formId", line.formId);
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}


$(document).ready(function () {
    //监听点击页码操作
    $("body").delegate(".page>span", "click", function () {
        // alert(123);
        var number = $(this).html();
        // alert(number);
        getMsg(number);

        $(this).addClass("cur");
        $(this).siblings().removeClass("cur");
        // alert("456");
    });


});




















