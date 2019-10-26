$(document).ready(function () {
    var commited = false;
    var commitedCount=0;

    //切换
    var li = document.getElementsByClassName('index');
    var contant = document.getElementsByClassName("contant");
    for (var i = 0; i < li.length; i++) {
        li[i].index = i;
        li[i].onclick = function () {
            for (var i = 0; i < li.length; i++) {
                li[i].className = "index";
                contant[i].style.display = "none";
            }
            li[this.index].classList.add("current");
            contant[this.index].style.display = "block";
        }
    }
//提交表单信息
    var stuId = document.getElementsByClassName("repair-information")[2];
    var stuName = document.getElementsByClassName("repair-information")[0];
    var stuPhone = document.getElementsByClassName("repair-information")[1];
    var stuMail = document.getElementsByClassName("repair-information")[3];
    var formMsg = document.getElementsByClassName("textareaStyle")[0];
    var photoId = "-1";
    var a = new Date();
    var y = a.getFullYear();
    var day = a.getDate();
    var months = a.getMonth() + 1;
// var year=a.getFullYear();
    var radio = document.getElementsByClassName("radios");
    var month = document.getElementById("month");
    var days = document.getElementById("days");
    var times = document.getElementById("times");
// var wTypr=$(".radios").val();
    var appointment = document.getElementsByClassName("appoint")[0];
    var domiNumber = document.getElementsByClassName("dormitoryNumber")[0];
    var adress = document.getElementById("adress");
    var buliding = document.getElementById("buliding");
    var wType = "其他";
    $("body").delegate(".radios", "click", function () {
        for (var i = 0; i < radio.length; i++) {
            if (radio[i].checked) {
                wType = radio[i].value;
                break;
            }
        }
    });
    $("#month").append('<option value=' + months + '>' + months + '月' + '</option>');
    $("#days").append('<option value=' + day + '>' + day + '日' + '</option>');
    $("#days").append('<option value=' + (day + 1) + '>' + (day + 1) + '日' + '</option>');
    $("#days").append('<option value=' + (day + 2) + '>' + (day + 2) + '日' + '</option>');
    $("#days").append('<option value=' + (day + 3) + '>' + (day + 3) + '日' + '</option>');
    $("#days").append('<option value=' + (day + 4) + '>' + (day + 4) + '日' + '</option>');
    $("#days").append('<option value=' + (day + 5) + '>' + (day + 5) + '日' + '</option>');
    $("#days").append('<option value=' + (day + 6) + '>' + (day + 6) + '日' + '</option>');


    var button = $(".handin-tit");
    $("body").delegate(".handin-tit", "click", function () {
        judege = confirm("确认提交");
        //不验证无法发送成功
        var bool = check(stuId.value)&&check(stuName.value)&&check(stuPhone.value)&&check(buliding.value)&&check(domiNumber.value)&&check(stuMail.value)&&check(formMsg.value)&&check(times.value)&&check(days.value)&&check(wType);
        if(bool==false||bool===false)
        {
            alert("请填写正确的表单信息");
            return;
        }

        if(commited)
        {
            alert("正在提交...");
            return;
        }
        commited = true;
        if (judege) {
            $.ajax({
                type: "POST",
                url: "/student/submission/form",
                dataType: "json",
                data: JSON.stringify({
                    "stuId": stuId.value,
                    "stuName": stuName.value,
                    "stuPhone": stuPhone.value,
                    "stuMail": stuMail.value,
                    "formMsg": formMsg.value,
                    "photoId": -1,
                    "wType": wType,
                    "room": adress.value + buliding.value + domiNumber.value,
                    "appointment": parseInt(times.value),
                     "appointDate": month.value + '-' + days.value,
                }),
                success: function (msg) {
                    if (msg.code == 201) {
                        alert("报修单提交成功");

                    }else if(msg.code==405)
                    {
                        alert("您已经提交过了,一分钟后再交");
                    }
                        commited = false;
                },
                error: function (xhr) {
                    alert("后台服务器检测到异常：请检查一下表单信息是否填写正确");
                    commited=false;
                }
            });
        }else{
            alert("放弃提交");
            commited = false;
        }

    });

    function check(str) {
        try{
            if(str===undefined) return false;
            if(str == null||str==='') return false;
            if((str+'').length<=0) return false;



        }catch (e) {
            console.log(e);
            return false;
        }
        return true;

    }


});
//进度查询
gerRepairOrder(1);
var page=document.getElementsByClassName("page")[0];
function gerRepairOrder(pageCount){
    $.ajax({
        type: "POST",
        url: "/student/incomplete/history/form",
        dataType: "json",
        data: JSON.stringify({
            "page":pageCount,
            "limit":3,
        }),
        success:function(msg){
            alert("123");
            console.log(msg);
            var page=$(".page");
            // $(".page").html("");
            $(".orderContant").html("");
            var data=msg.data;
            if($(".page").children().length==0){
                for(var i=1;i<=msg.totalPage;i++){
                    page.append('<span class="page-number">'+i+'</span>');
                }
            }
            for(var i=0;i<msg.size;i++){
                if(data[i].queryCode=="0"){
                    condition="待排期"
                }
                else if(data[i].queryCode=="1"){
                    condition="已排期"
                }
                else if(data[i].queryCode=="2"){
                    condition="待确认"
                }
                else{
                    condition="异常"
                }
                $(".orderContant").append('<div class="order"></div>');
                $(".order").eq(i).append('<i class="yellowLabel"></i><span class="state-tit">'+condition+'</span>');
                $(".order").eq(i).append('<div class="orderInformation"></div>');
                $(".orderInformation").eq(i).append('<div class="orderInside">'+
                    '<p class="order-tit">报修人：'+data[i].stuName+'</p>'+
                    '<p class="order-tit">报修地址：'+data[i].room+'</p>'+
                    '<p class="order-tit">报修电话：'+data[i].stuPhone+'</p>'+
                    '<p class="order-tit">报修内容：'+data[i].formMsg+'</p>'+
                    '</div>')
                $(".orderInformation").eq(i).append('<div class="orderImg"><img src="img/head1.jpg"></div>')
                $(".orderInformation").eq(i).append('<button class="finish">确认完成</button>')
                $(".orderInformation").eq(i).attr("formId", data[i].formId);
                if(condition=="待确认"||condition=="异常"){
                    $(".orderInformation").eq(i).append('<button class="again">一键再修</button>')
                }
            }
        },
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//学生确认报修完成
function insureFinish(formId){
    $.ajax({
        type: "POST",
        url: "/student/confirmation/form",
        dataType: "json",
        data: JSON.stringify({
            "formId":formId
        }),
        success:function(msg){
            if(msg.code=="201"){
                alert("确认完成");
            }
        },
        error:function (xhr) { 
            alert(xhr.status);
         }
    })
}
//监听学生点击确认按钮
$("body").delegate(".finish", "click", function () {
    formId = $(this).parent().attr("formid");
    alert(formId);
    insureFinish(formId);
});
//监听点击页码
$("body").delegate(".page>span","click",function(){
    let page= $(this).html();
    // alert(page);
    gerRepairOrder(page);
    $(this).addClass("cur");
    $(this).siblings().removeClass("cur");
})
//监听一键再修按钮
var againDiv=document.getElementsByClassName("againDiv")[0];
$("body").delegate(".again","click",function(){
    // alert("133");
    againDiv.style.display="block";
    againDiv.scrollIntoView({
        behavior:'smooth'//平滑的移过去
    })
    formId = $(this).parent().attr("formid");
    alert(formId);
    var a=new Date();
    $("#againmonth").html("");
    $("#againday").html("");
    // $("#again").html("");
    $("#againmonth").append('<option value="'+(a.getMonth()+1)+'" label="'+(a.getMonth()+1)+'月">');
    $("#againday").append('<option value="'+(a.getDate())+'" label="'+a.getDate()+'日">');
    $("#againday").append('<option value="'+(a.getDate()+1)+'" label="'+(a.getDate()+1)+'日">');
    $("#againday").append('<option value="'+(a.getDate()+2)+'" label="'+(a.getDate()+2)+'日">');
    $("#againday").append('<option value="'+(a.getDate()+3)+'" label="'+(a.getDate()+3)+'日">');
    $("#againday").append('<option value="'+(a.getDate()+4)+'" label="'+(a.getDate()+4)+'日">');
    $("#againday").append('<option value="'+(a.getDate()+5)+'" label="'+(a.getDate()+5)+'日">');
    $("#againday").append('<option value="'+(a.getDate()+6)+'" label="'+(a.getDate()+6)+'日">');
})
//监听一键再修里的确认按钮
var againmonth=document.getElementById("againmonth");
var againday=document.getElementById("againday");
var againtime=document.getElementById("againtime");
$("body").delegate(".againInsure","click",function(){
    var appointDate=againmonth.value+"="+againday.value;
    var appointment=againtime.value;
    againRepair(formId,appointDate,appointment);
    againDiv.style.display="none";
})
//一键再修方法
function againRepair(formId,appointDate,appointment){
    $.ajax({
        type: "PUT",
        url: "/student/appoint",
        dataType: "json",
        data: JSON.stringify({
            "formId":parseInt(formId),
            "appointDate":appointDate,
            "appointment":parseInt(appointment)
        }),
        success:function(msg){
            if(msg.code=="201"){
                alert("一键再修成功");
            }
        },
        error:function (xhr) { 
            alert(xhr.status);
         }
    })
}
//监听重新预约时间里的叉
$("body").delegate(".icon-cha","click",function(){
    againDiv.style.display="none";
});