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
        // alert();
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
                async:false,
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
                        //todo: 快看这里
                        onloadFile();
                        alert("报修单提交成功");
                        location.reload();

                    }else if(msg.code==405)
                    {
                        alert("您已经提交过了,一分钟后再交");
                    }else if(msg.code==401)
                    {
                        alert("检测到敏感词汇！"+msg.desc+"无法提交！");
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
            "page":parseInt(pageCount),
            "limit":3,
        }),
        success:function(msg){
            var page=$(".page");
            // $(".page").html("");

            $(".orderContant").html("");
            var data=msg.data;
            if($(".page").children().length==0){
                for(var i=1;i<=msg.totalPage;i++){
                    page.append('<span class="page-number">'+i+'</span>');
                    if(i==1){
                        $(".page-number").eq(0).addClass("cur");
                    }
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
    gerRepairOrder(page);
});
//监听点击页码
$("body").delegate(".page>span","click",function(){
    page= $(this).html();
    if(orderContant.style.display=="block"){
        gerRepairOrder(page);
    }
    else{
        gerfinishOrder(page);
    }
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
//切换未完成和已完成
var iconLabel=document.getElementsByClassName("iconLabel");
var orderContant=document.getElementsByClassName("orderContant")[0];
var finishOrderContant=document.getElementsByClassName("finishOrderContant")[0];
iconLabel[0].onclick=function(){
    orderContant.style.display="block";
    finishOrderContant.style.display="none";
    $(".label-tit").eq(0).addClass("cur-tit");
    $(".label-tit").eq(1).removeClass("cur-tit");
    $(".iconLabel").eq(0).addClass("curLabel");
    $(".iconLabel").eq(1).removeClass("curLabel");
    $(".page").html("");
    gerRepairOrder(1);
}
iconLabel[1].onclick=function(){
    orderContant.style.display="none";
    finishOrderContant.style.display="block";
    $(".label-tit").eq(1).addClass("cur-tit");
    $(".label-tit").eq(0).removeClass("cur-tit");
    $(".iconLabel").eq(1).addClass("curLabel");
    $(".iconLabel").eq(0).removeClass("curLabel");
    $(".page").html("");
    gerfinishOrder(1);
}
//获得已完成的信息
function gerfinishOrder(pageCount){
    $.ajax({
        type: "POST",
        url: "/student/complete/history",
        dataType: "json",
        data: JSON.stringify({
            "page":parseInt(pageCount),
            "limit":3,
        }),
        success:function(msg){
            console.log(msg);
            $(".finishOrderContant").html("");
            var data=msg.data;
            if($(".page").children().length==0){
                for(var i=1;i<=msg.totalPage;i++){
                    let span=document.createElement('span');
                    span.className="page-number";
                    span.innerText=i;
                    $(".page").append(span);
                    if(i==1){
                        $(".page-number").eq(0).addClass("cur");
                    }
                }
            }

            for(var i=0;i<msg.size;i++){
                $(".finishOrderContant").append('<div class="finishorder"></div>');
                $(".finishorder").eq(i).append('<i class="yellowLabel"></i><span class="finishstate-tit">'+"已完成"+'</span>');
                $(".finishorder").eq(i).append('<div class="finishorderInformation"></div>');
                $(".finishorderInformation").eq(i).append('<div class="finishorderInside">'+
                    '<p class="finishorder-tit">报修人：'+data[i].stuName+'</p>'+
                    '<p class="finishorder-tit">报修地址：'+data[i].room+'</p>'+
                    '<p class="finishorder-tit">报修电话：'+data[i].stuPhone+'</p>'+
                    '<p class="finishorder-tit">报修内容：'+data[i].formMsg+'</p>'+
                    '</div>')
                $(".finishorderInformation").eq(i).append('<div class="finishorderImg"><img src="img/head1.jpg"></div>')
                if(data[i].queryCode!=4){
                    $(".finishorderInformation").eq(i).append('<button class="comment">评价</button>')
                }

                $(".finishorderInformation").eq(i).attr("wKey", data[i].wKey);
                $(".finishorderInformation").eq(i).attr("formId", data[i].formId);
            }
        },
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//监听点击评价按钮
var evaluate=document.getElementsByClassName("evaluate")[0];
$("body").delegate(".comment","click",function () {
    evaluate.style.display="block";
    evaluate.scrollIntoView({
        behavior:'smooth'//平滑的移过去
    })
    wKey=$(this).parent().attr("wKey");
    formId=$(this).parent().attr("formId");
})
//监听评价单选框
var commentArea=document.getElementsByClassName("commentArea")[0];
var evaluateRadio=document.getElementsByClassName("evaluateRadio");
$("body").delegate(".evaluateRadio", "click", function () {
    for (var i = 0; i < evaluateRadio.length; i++) {
        if (evaluateRadio[i].checked) {
            evaluation = evaluateRadio[i].value;
            break;
        }
    }

});
//学生评价方法

function stuEvaluation(evaluation,wKey,massage,formId){
    $.ajax({
        type: "POST",
        url: "/student/evaluate/detail",
        dataType: "json",
        async:false,
        data: JSON.stringify({
            "evaluation":evaluation,
            "wKey":wKey,
            "msg":massage,
            "formId":parseInt(formId),
        }),
        success:function(msg){
            if(msg.code==201){
                alert("评价成功");
                // evaluate.style.display="none";
            }else if (msg.code==401){
                alert("检测到敏感词！"+msg.desc+"请修改后再评价！")
            }
        },
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//监听评价里的叉
$("body").delegate(".icon-chaa", "click", function () {
    evaluate.style.display="none";
});

//监听评价里的确认
$("body").delegate(".evaluateSure","click",function () {
    massage=commentArea.value;
    stuEvaluation(evaluation,wKey,massage,formId);
    evaluate.style.display="none";
    gerfinishOrder(page);
    // $(this).html("")
})
$("body").delegate(".bye","click",function () { 
    window.location.href="/login.html"
})
//上传文件
function onloadFile(){
    document.photo.submit();
}

//监听历史公告的叉
var historyNotice=document.getElementsByClassName("historyNotice")[0];
$("body").delegate(".history-cha", "click", function () {
    historyNotice.style.display="none";
});
//监听公告点击
$("body").delegate(".history", "click", function () {
    historyNotice.style.display="block";
});
//获得所有历史公告
var historyContant=document.getElementsByClassName("historyContant")[0];
$.ajax({
    type: "POST",
    url: "/student/history/board",
    dataType: "json",
    async:true,
    success:function(msg){
        if(msg.code==200){
            var data=msg.data;
            for(var i=0;i<msg.size;i++){
                $(".historyContant").append('<div class="historyInside"></div>');
                $(".historyInside").eq(i).append('<div class="boardMsg">'+data[i].boardMsg+'</div>');
                $(".historyInside").eq(i).append('<div class="his-date">'+data[i].date+'</div>');
            }
        }
    },
    error:function(xhr){
        alert(xhr.status);
    }
})
//显示略所图
function previewFile() {
    // 通过标签选择器获取HTML元素
    var preview = document.querySelector('.saveImg>img');
    var file = document.querySelector('input[type=file]').files[0];
    // Js内置文件流
    var reader = new FileReader();

    // 更新img标签的src属性为图片的本地路径，就可以显示了
    reader.onloadend = function () {
        preview.src = reader.result;
    }

    // 图片文件不空就显示
    if (file) {
        reader.readAsDataURL(file);
    } else {
        // 图片文件是空的
        preview.src = "";
    }
}
