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



$(document).ready(function () {
    //监听点击处理按钮
    $("body").delegate(".page>span", "click", function () {
        var number = $(this).html();
        getData(number);
        $(this).addClass("cur");
        $(this).siblings().removeClass("cur");
    });
//监听点击处理按钮
    var contant = document.getElementsByClassName("contant")[0];
    var dealOrder = document.getElementsByClassName("dealOrder")[0];
    var visited = false;
    $("body").delegate(".col>.deal", "click", function () {
        formId = $(this).parent().parent().attr("formid");

        contant.style.display = "none";
        dealOrder.style.display = "block";
        getFormDetail(formId);
        try{
            if(visited){return;}
            visited = true;

            getWorker();
            visited = false;
        }catch (e) {
        }
        let selectDay=document.createElement('select');
        let selectTime=document.createElement('select');
        selectDay.className="selectDay";
        selectTime.className="selectTime";
        var a=new Date();
        var day=a.getDate();
        $(".leftContant").append(selectDay);
        $(".leftContant").append(selectTime);

            $(".selectDay").append('<option value="'+day+'">'+day+'日'+'</option>');
            $(".selectDay").append('<option value="'+(day+1)+'">'+(day+1)+'日'+'</option>');
            $(".selectDay").append('<option value="'+(day+2)+'">'+(day+2)+'日'+'</option>');
            $(".selectDay").append('<option value="'+(day+3)+'">'+(day+3)+'日'+'</option>');
            $(".selectDay").append('<option value="'+(day+4)+'">'+(day+4)+'日'+'</option>');
            $(".selectDay").append('<option value="'+(day+5)+'">'+(day+5)+'日'+'</option>');
            $(".selectDay").append('<option value="'+(day+6)+'">'+(day+6)+'日'+'</option>');
            $(".selectTime").append('<option value="9">9点</option>');
            $(".selectTime").append('<option value="10">10点</option>');
            $(".selectTime").append('<option value="11">11点</option>');
            $(".selectTime").append('<option value="15">15点</option>');
            $(".selectTime").append('<option value="16">16点</option>');
            $(".selectTime").append('<option value="17">17点</option>');
            $(".selectTime").append('<option value="18">18点</option>');


    });
//监听点击返回上一级按钮
// var returnback=document.getElementsByClassName("returnBack")[0];
    $("body").delegate(".returnBack", "click", function () {
        contant.style.display = "block";
        dealOrder.style.display = "none";
    });

});


function getMsg(pageCount) {
    $.ajax({
        type: "POST",
        url: "/admin/incomplete/form",
        dataType: "json",
        async:false,
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
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}


function getFormDetail(formId) {

    $.ajax({
        type: "POST",
        url: "/admin/incomplete/formId",
        dataType: "json",
        async:false,
        data: JSON.stringify({
            "formId": formId,
        }),
        success: function (msg) {
            $(".information").html("");
            var data = msg.data;
            let img=document.createElement('img');
            $(img).attr("src","img/jindutiao.png");
            $(".information").append('<p>报修人：' + data[0].stuName);
            $(".information").append('<p>报修电话：' + data[0].stuPhone);
            $(".information").append('<p>学号：' + data[0].stuId);
            $(".information").append('<p>学生邮箱：' + data[0].stuMail);
            $(".information").append('<p>地址：' + data[0].room);
            $(".information").append('<p>报修类型：' + data[0].wType);
            $(".information").append('<p>预约时间：' + data[0].appointDate+data[0].appointment+'点');
            $(".information").append('<p>报修内容：' + data[0].formMsg);
            $(".information").append('<p>图片：'+img.src+'</p >');
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

// str.substring(0,str.length-2);

function getWorker(){

    var wType=$(".information>p").eq(5).html().split("：")[1];

    var date8=$(".information>p").eq(6).html().split("：")[1].split(" ")[0];

    var str888=$(".information>p").eq(6).html().split("：")[1].split(" ")[1];
  
    var hour=(str888||"").split("点");
    $('.workerInside').html("");
    $.ajax({
        type:"POST",
        url:"/admin/worker",
        dataType: "json",
        async:false,
        data: JSON.stringify({
            "date":date8,
            "hour":parseInt(hour[0]),
            "wType":wType
        }),
        success:function(msg){
            // console.log(msg);
            var data=msg.data;

            for(var i=0;i<data.length;i++){
                let divchoseWorker=document.createElement('div');
                divchoseWorker.className="choseWorker";
                let input=document.createElement('input')
                input.type="radio";
                input.name="worke";
                let workContantdiv=document.createElement('div');
                workContantdiv.className="workerContant";
                let nameDiv=document.createElement('div');
                nameDiv.className="Name";
                let leftContantDiv=document.createElement('div');
                leftContantDiv.className="leftContant";
                let rightContantDiv=document.createElement('div');
                rightContantDiv.className="rightContant";
                $('.workerInside').append(divchoseWorker);
                $('.choseWorker').eq(i).append(input);
                $('input[name="worke"]').eq(i).attr("number",i);
                $('.choseWorker').eq(i).append(workContantdiv)
                $('.workerContant').eq(i).append(nameDiv);
                $('.Name').eq(i).html("姓名："+data[i].wName);
                $('.workerContant').eq(i).append(leftContantDiv)
                $('.leftContant').eq(i).append('<p>工种：'+data[i].wType+'</p >')
                $('.leftContant').eq(i).append('<p>联系电话：'+data[i].wTel+'</p >')
                $('.workerContant').eq(i).append(rightContantDiv)
                $('.choseWorker').eq(i).attr("wKey",data[i].wKey);

            }
        },
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//监听点击工人按钮
$("body").delegate('input[name="worke"]','click',function(){
    workeNumber = $(this).attr("number");
    // alert(workeNumber);
})
//监听安排工人按钮
$("body").delegate('.arrangeWroker','click',function(){
    arrangeWorker(formId,workeNumber);
});
var dealOrder=document.getElementsByClassName("dealOrder")[0];
var success=document.getElementsByClassName("success")[0];
var successback=document.getElementsByClassName("successback")[0];
var contant=document.getElementsByClassName("contant")[0];
$("body").delegate('.successback','click',function(){
    successback.style.display="none";
    dealOrder.style.display="block";
})
//点击提交安排工人按钮
function arrangeWorker(formid,workeNumber){
    var information=document.getElementsByClassName("information")[0];
    var MailP=information.getElementsByTagName("p")[3].textContent;
    var Mail=MailP.split("：")[1];
    var wKey=$('.choseWorker').eq(workeNumber).attr("wKey");
    var day=document.getElementsByTagName('select')[workeNumber*2+1];
    var hour=document.getElementsByTagName('select')[workeNumber*2+2];
    var wTel=$(".leftContant>p").eq(1).html().split("：")[1];
    $.ajax({
        type:"PUT",
        url:"/admin/arrangement/form",
        dataType: "json",
        async:false,
        data: JSON.stringify({
            "wKey":parseInt(wKey),
            "formId":parseInt(formid),
            "stuMail":Mail,
            "day":parseInt(day.value),
            "hour":parseInt(hour.value),
            "wTel":wTel
        }),
        success:function (msg) {
            if(msg.code==201){
                // dealOrder.display="none";
                // contant.display="none";
                // success.display="block";
                alert("排期成功");
                location.reload();
            }
        },
        error:function (xhr) {
            alert(xhr.status);
        }
    })
}
//监听删除按钮
$("body").delegate(".del","click",function () { 
    formId=$(this).parent().parent().attr("formId");
    var judge= confirm("是否删除该报修单");
    if(judge){
        delOrder(formId);
    }

    getMsg(1);

})
//删除报修订单
function delOrder(formId){
    $.ajax({
        type:"POST",
        url:"/admin/delete/form",
        dataType:"json",
        data:JSON.stringify({
            "formId":formId
        }),
        success:function (msg) {
            if(msg.code==201){
                alert("报修单已删除！");
                location.reload();
            }
        },
        error:function (xhr) {
            alert(xhr.status);
        }
    })
}














