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
            var table = $(".repairItem");
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
                $(".row").eq(i+1).attr("formId", line.formId);
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
//监听点击处理按钮
$("body").delegate(".page>span", "click", function () {
    var number = $(this).html();
    getData(number);
    $(this).addClass("cur");
    $(this).siblings().removeClass("cur");
})
//监听点击处理按钮
var contant = document.getElementsByClassName("contant")[0];
var dealOrder = document.getElementsByClassName("dealOrder")[0];
$("body").delegate(".col>.deal", "click", function () {
    var formId = $(this).parent().parent().attr("formid");
    alert(formId)
    contant.style.display = "none";
    dealOrder.style.display = "block"
    getFormDetail(formId);
    getWorker();
})
//监听点击返回上一级按钮
// var returnback=document.getElementsByClassName("returnBack")[0];
$("body").delegate(".returnBack", "click", function () {
    contant.style.display = "block";
    dealOrder.style.display = "none";
})

function getFormDetail(formId) {
    $.ajax({
        type: "POST",
        url: "/admin/formId",
        dataType: "json",
        data: JSON.stringify({
            "formId": formId,
        }),
        success: function (msg) {
            $(".information").html("");
            var data = msg.data;
            $(".information").append('<p>报修人：' + data[0].stuName);
            $(".information").append('<p>报修电话：' + data[0].stuPhone);
            $(".information").append('<p>学号：' + data[0].stuId);
            $(".information").append('<p>学生邮箱：' + data[0].stuMail);
            $(".information").append('<p>地址：' + data[0].room);
            $(".information").append('<p>报修类型：' + data[0].wType);
            $(".information").append('<p>预约时间：' + data[0].appointDate+data[0].appointment+'点');
            $(".information").append('<p>报修内容：' + data[0].formMsg);
            $(".information").append('<p>图片：');
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}
function getWorker(){
    var res=$(".information>p").eq(6).html().split("：")
    var wType=$(".information>p").eq(5).html().split("：")[1];
    var date=$(res)[1].split(" ")[0];
    var hour=$(res)[1].split(" ")[1].split("点")[0];
    $.ajax({
        type:"POST",
        url:"",
        dataType: "json",
        data: JSON.stringify({
            "date":date,
            "hour":parseInt(hour),
            "wType":wType
        }),
        success:function(msg){
            var data=msg.data;
            $('.workerInside').html("");
            for(var i=0;i<data.length;i++){
                $('.workerInside').append('<div class="choseWorker"></div>')
                $('.choseWorker').append('<input type="radio"  name="worke">')
                $('.choseWorker').append('<div class="workerContant"></div>')
                $('.workerContant').append('<div class="Name">姓名'+data[i].wName+'</div>')
                $('.workerContant').append('<div class="leftContant"></div>')
                $('.leftContant').append('<p>工种：'+data[i].wType+'</p >')
                $('.leftContant').append('<p>联系电话：'+data[i].wTel+'</p >')
                $('.workerContant').append('<div class="rightContant"></div>')
                // $('.leftContant').append('<select class=""></select>')
            }
        },
        error:function(xhr){
            alert(xhr.status);
        }
    })
}




















