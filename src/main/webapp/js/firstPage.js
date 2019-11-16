// $(document).ready(function () {
var commited = false;
var commitedCount = 0;

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
page = 1;

//提交表单信息
var stuId = document.getElementsByClassName("repair-information")[2];
var stuName = document.getElementsByClassName("repair-information")[0];
var stuPhone = document.getElementsByClassName("repair-information")[1];
var stuMail = document.getElementsByClassName("repair-information")[3];
var email=document.getElementById("email");
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
//cookie自动填充表单信息
stuId.value = decodeURI(getCookie("stuId"));
stuName.value = decodeURI(getCookie("stuName"));
//自动生成南北苑宿舍
$("body").delegate("#adress", "click", function () {
    // alert("111111");
    $("#buliding").html("");
    if (adress.value == "南苑") {
        $("#buliding").append('<option value="1栋">1栋</option><option value="2栋">2栋</option><option value="3栋">3栋</option><option value="4栋">4栋</option><option value="5栋">5栋</option><option value="6栋">6栋</option><option value="7栋">7栋</option><option value="8栋">8栋</option><option value="9栋">9栋</option><option value="10栋">10栋</option><option value="11栋">11栋</option><option value="11栋">12栋</option><option value="11栋">12栋</option><option value="13栋">13栋</option><option value="14栋">14栋</option><option value="15栋">15栋</option>')
    } else {
        $("#buliding").append('<option value="16栋">16栋</option><option value="17栋">17栋</option><option value="18栋">18栋</option><option value="19栋">19栋</option><option value="20栋">20栋</option><option value="21栋">21栋</option><option value="22栋">22栋</option><option value="23栋">23栋</option><option value="24栋">24栋</option><option value="25栋">25栋')
    }
});
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
    var bool = check(stuId.value) && check(stuName.value) && check(stuPhone.value) && check(buliding.value) && check(domiNumber.value) && check(stuMail.value) && check(formMsg.value) && check(times.value) && check(days.value) && check(wType);
    // alert();
    if (bool == false || bool === false) {
        alert("请填写正确的表单信息");
        return;
    }

    if (commited) {
        alert("正在提交...");
        return;
    }
    commited = true;
    if (judege) {
        $.ajax({
            type: "POST",
            async: false,
            url: "/student/submission/form",
            dataType: "json",
            data: JSON.stringify({
                "stuId": stuId.value,
                "stuName": stuName.value,
                "stuPhone": stuPhone.value,
                "stuMail": stuMail.value+email.value,
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

                } else if (msg.code == 400) {
                    alert("手机号码错误！请检查手机号码是否正确！");
                } else if (msg.code == 405) {
                    alert("您已经提交过了,一分钟后再交");
                } else if (msg.code == 401) {
                    alert("检测到敏感词汇！" + msg.desc + "无法提交！");
                }
                commited = false;
            },
            error: function (xhr) {
                alert("后台服务器检测到异常：请检查一下表单信息是否填写正确");
                commited = false;
            }
        });
    } else {
        alert("放弃提交");
        commited = false;
    }

});

function check(str) {
    try {
        if (str === undefined) return false;
        if (str == null || str === '') return false;
        if ((str + '').length <= 0) return false;


    } catch (e) {
        console.log(e);
        return false;
    }
    return true;

}


// });
//进度查询
gerRepairOrder(1);

// var page=document.getElementsByClassName("page")[0];
function gerRepairOrder(pageCount) {
    $.ajax({
        type: "POST",
        url: "/student/incomplete/history/form",
        dataType: "json",
        data: JSON.stringify({
            "page": parseInt(pageCount),
            "limit": 3,
        }),
        success: function (msg) {
            pageCount=parseInt(pageCount);
            var page = $(".page");
            // $(".page").html("");
            console.log(msg)
            $(".orderContant").html("");
            var data = msg.data;
            if ($(".page").children().length == 0) {
                if(msg.totalPage<=8){
                    for (var i = 1; i <= msg.totalPage; i++) {
                        page.append('<span class="page-number">' + i + '</span>');
                        if (i == 1) {
                            $(".page-number").eq(0).addClass("cur");
                        }
                    }
                    $(".page-number").eq(0).addClass("cur");
                }
                else{
                    if(pageCount<=3 || pageCount>=msg.totalPage-2){
                        for(var i=1;i<=3;i++){
                            page.append('<span class="page-number">' + i + '</span>');
                        }
                        if(pageCount==3){
                            page.append('<span class="page-number">4</span>')
                        }
                        page.append('<span>...</span>');
                        for(var i=msg.totalPage-2;i<=msg.totalPage;i++){
                            page.append('<span class="page-number">' + i + '</span>');
                        }
                    }
                    else if(pageCount>3 && pageCount<msg.totalPage-2){

                        if(pageCount!=msg.totalPage-3){
                            page.append('<span class="page-number">1</span><span>...</span>');
                            for(var i=parseInt(pageCount)-1;i<=parseInt(pageCount)+1;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                            page.append('<span>...</span>');
                            for(var i=msg.totalPage-2;i<=msg.totalPage;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                        }
                        else{
                            for(var i=1;i<=3;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                            page.append('<span>...</span>');
                            for(var i=msg.totalPage-3;i<=msg.totalPage;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                        }



                    }
                }
            }
            for (var i = 0; i < msg.size; i++) {
                $(".orderContant").append('<div class="order"></div>');
                $(".order").eq(i).append('<i class="progressBat"></i>');
                if (data[i].queryCode == "0") {
                    condition = "待排期"
                    $(".progressBat").eq(i).addClass("progressFirst");
                } else if (data[i].queryCode == "1") {
                    condition = "已排期"
                    $(".progressBat").eq(i).addClass("progressSecond");
                } else if (data[i].queryCode == "2") {
                    condition = "待确认"
                    $(".progressBat").eq(i).addClass("progressThird");
                } else {
                    condition = "异常"
                    $(".progressBat").eq(i).addClass("progressWrong");
                }
                $(".order").eq(i).append('<i class="yellowLabel"></i><span class="state-tit">' + condition + '</span>');
                $(".order").eq(i).append('<div class="orderInformation"></div>');
                $(".orderInformation").eq(i).append('<div class="orderInside">' +
                    '<p class="order-tit">报修人：' + data[i].stuName + '</p>' +
                    '<p class="order-tit">报修地址：' + data[i].room + '</p>' +
                    '<p class="order-tit">报修电话：' + data[i].stuPhone + '</p>' +
                    '<p class="order-tit">报修内容：' + data[i].formMsg + '</p>' +
                    '</div>')
                $(".orderInformation").eq(i).append('<div class="orderImg"></div>')
                getPhoto(data[i].formId, i, 0);
                $(".orderInformation").eq(i).append('<button class="finish">确认完成</button>')
                $(".orderInformation").eq(i).attr("formId", data[i].formId);
                if (condition == "待确认" || condition == "异常") {
                    $(".orderInformation").eq(i).append('<button class="again">一键再修</button>')
                }
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

//学生确认报修完成
function insureFinish(formId) {
    $.ajax({
        type: "POST",
        url: "/student/confirmation/form",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "formId": formId
        }),
        success: function (msg) {
            if (msg.code == "201") {
                alert("确认完成");
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

//监听学生点击确认按钮
$("body").delegate(".finish", "click", function () {
    formId = $(this).parent().attr("formid");
    var judge = confirm("是否确认已完成");
    if (judge) {
        insureFinish(formId);
        gerRepairOrder(page);
    }
    // alert(formId);

});
//监听点击页码
$("body").delegate(".page>span", "click", function () {

    page = $(this).html();
    // $(".page").html("")
    if (orderContant.style.display == "block") {
        gerRepairOrder(page);
    } else {
        gerfinishOrder(page);
    }
    var pageNum=document.getElementsByClassName("page-number");
    for(var i=0;i<pageNum.length;i++){
        pageNum[i].className="page-number";
    }
    for(var i=0;i<pageNum.length;i++){
        if($(".page-number").eq(i).html()==page){
            $(".page-number").eq(i).addClass("cur")
        }
    }
    // $(this).addClass("cur");
    // $(this).siblings().removeClass("cur");
})
//监听一键再修按钮
var againDiv = document.getElementsByClassName("againDiv")[0];
$("body").delegate(".again", "click", function () {
    // alert("133");
    againDiv.style.display = "block";
    againDiv.scrollIntoView({
        behavior: 'smooth'//平滑的移过去
    })
    formId = $(this).parent().attr("formid");
    // alert(formId);
    var a = new Date();
    $("#againmonth").html("");
    $("#againday").html("");
    // $("#again").html("");
    $("#againmonth").append('<option value="' + (a.getMonth() + 1) + '" label="' + (a.getMonth() + 1) + '月">');
    $("#againday").append('<option value="' + (a.getDate()) + '" label="' + a.getDate() + '日">');
    $("#againday").append('<option value="' + (a.getDate() + 1) + '" label="' + (a.getDate() + 1) + '日">');
    $("#againday").append('<option value="' + (a.getDate() + 2) + '" label="' + (a.getDate() + 2) + '日">');
    $("#againday").append('<option value="' + (a.getDate() + 3) + '" label="' + (a.getDate() + 3) + '日">');
    $("#againday").append('<option value="' + (a.getDate() + 4) + '" label="' + (a.getDate() + 4) + '日">');
    $("#againday").append('<option value="' + (a.getDate() + 5) + '" label="' + (a.getDate() + 5) + '日">');
    $("#againday").append('<option value="' + (a.getDate() + 6) + '" label="' + (a.getDate() + 6) + '日">');
})
//监听一键再修里的确认按钮
var againmonth = document.getElementById("againmonth");
var againday = document.getElementById("againday");
var againtime = document.getElementById("againtime");
$("body").delegate(".againInsure", "click", function () {
    var appointDate = againmonth.value + "-" + againday.value;
    var appointment = againtime.value;
    againRepair(formId, appointDate, appointment);
    againDiv.style.display = "none";
    gerRepairOrder(page);
})

//一键再修方法
function againRepair(formId, appointDate, appointment) {
    $.ajax({
        type: "PUT",
        url: "/student/appoint",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "formId": parseInt(formId),
            "appointDate": appointDate,
            "appointment": parseInt(appointment)
        }),
        success: function (msg) {
            if (msg.code == "201") {
                alert("一键再修成功");
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

//监听重新预约时间里的叉
$("body").delegate(".icon-cha", "click", function () {
    againDiv.style.display = "none";
});
//切换未完成和已完成
var iconLabel = document.getElementsByClassName("iconLabel");
var orderContant = document.getElementsByClassName("orderContant")[0];
var finishOrderContant = document.getElementsByClassName("finishOrderContant")[0];
iconLabel[0].onclick = function () {
    orderContant.style.display = "block";
    finishOrderContant.style.display = "none";
    $(".label-tit").eq(0).addClass("cur-tit");
    $(".label-tit").eq(1).removeClass("cur-tit");
    $(".iconLabel").eq(0).addClass("curLabel");
    $(".iconLabel").eq(1).removeClass("curLabel");
    $(".page").html("");
    gerRepairOrder(1);
}
iconLabel[1].onclick = function () {
    orderContant.style.display = "none";
    finishOrderContant.style.display = "block";
    $(".label-tit").eq(1).addClass("cur-tit");
    $(".label-tit").eq(0).removeClass("cur-tit");
    $(".iconLabel").eq(1).addClass("curLabel");
    $(".iconLabel").eq(0).removeClass("curLabel");
    $(".page").html("");
    gerfinishOrder(1);
}

//获得已完成的信息
function gerfinishOrder(pageCount) {
    $.ajax({
        type: "POST",
        url: "/student/complete/history",
        dataType: "json",
        async:false,
        data: JSON.stringify({
            "page": parseInt(pageCount),
            "limit": 3,
        }),
        success: function (msg) {
            pageCount=parseInt(pageCount);
            var page=$(".page");
            $(".finishOrderContant").html("");
            $(".page").html("");
            var data = msg.data;
            if ($(".page").children().length == 0) {
                if(msg.totalPage<=8){
                    for (var i = 1; i <= msg.totalPage; i++) {
                        page.append('<span class="page-number">' + i + '</span>');
                        if (i == 1) {
                            $(".page-number").eq(0).addClass("cur");
                        }
                    }
                }
                else{
                    if(pageCount<=3 || pageCount>=msg.totalPage-2){
                        for(var i=1;i<=3;i++){
                            page.append('<span class="page-number">' + i + '</span>');
                        }
                        if(pageCount==3){
                            page.append('<span class="page-number">4</span>')
                        }
                        page.append('<span>...</span>');
                        for(var i=msg.totalPage-2;i<=msg.totalPage;i++){
                            page.append('<span class="page-number">' + i + '</span>');
                        }
                        $(".page-number").eq(0).addClass("cur");
                    }
                    else if(pageCount>3 && pageCount<msg.totalPage-2){

                        if(pageCount!=msg.totalPage-3){
                            page.append('<span class="page-number">1</span><span>...</span>');
                            for(var i=parseInt(pageCount)-1;i<=parseInt(pageCount)+1;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                            page.append('<span>...</span>');
                            for(var i=msg.totalPage-2;i<=msg.totalPage;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                        }
                        else{
                            for(var i=1;i<=3;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                            page.append('<span>...</span>');
                            for(var i=msg.totalPage-3;i<=msg.totalPage;i++){
                                page.append('<span class="page-number">' + i + '</span>');
                            }
                        }



                    }
                }
            }

            for (var i = 0; i < msg.size; i++) {
                $(".finishOrderContant").append('<div class="finishorder"></div>');
                $(".finishorder").eq(i).append('<i class="yellowLabel"></i><span class="finishstate-tit">' + "已完成" + '</span>');
                $(".finishorder").eq(i).append('<div class="finishorderInformation"></div>');
                $(".finishorderInformation").eq(i).append('<div class="finishorderInside">' +
                    '<p class="finishorder-tit">报修人：' + data[i].stuName + '</p>' +
                    '<p class="finishorder-tit">报修地址：' + data[i].room + '</p>' +
                    '<p class="finishorder-tit">报修电话：' + data[i].stuPhone + '</p>' +
                    '<p class="finishorder-tit">报修内容：' + data[i].formMsg + '</p>' +
                    '</div>')
                $(".finishorderInformation").eq(i).append('<div class="finishorderImg"></div>')
                getPhoto(data[i].formId, i, 1);
                console.log(1);
                if (data[i].queryCode != 4) {
                    $(".finishorderInformation").eq(i).append('<button class="comment">评价</button>')
                }

                $(".finishorderInformation").eq(i).attr("wKey", data[i].wKey);
                $(".finishorderInformation").eq(i).attr("formId", data[i].formId);
            }
        },
        error: function (xhr) {
            // alert(xhr.status);
        }
    })
}

//监听点击评价按钮
var evaluate = document.getElementsByClassName("evaluate")[0];
$("body").delegate(".comment", "click", function () {
    evaluate.style.display = "block";
    evaluate.scrollIntoView({
        behavior: 'smooth'//平滑的移过去
    })
    wKey = $(this).parent().attr("wKey");
    formId = $(this).parent().attr("formId");
})
//监听评价单选框
var commentArea = document.getElementsByClassName("commentArea")[0];
var evaluateRadio = document.getElementsByClassName("evaluateRadio");
$("body").delegate(".evaluateRadio", "click", function () {
    for (var i = 0; i < evaluateRadio.length; i++) {
        if (evaluateRadio[i].checked) {
            evaluation = evaluateRadio[i].value;
            break;
        }
    }

});

//学生评价方法

function stuEvaluation(evaluation, wKey, massage, formId) {
    // alert(123);
    $.ajax({
        type: "POST",
        url: "/student/evaluate/detail",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "evaluation": evaluation,
            "wKey": wKey,
            "msg": massage,
            "formId": parseInt(formId),
        }),
        success: function (msg) {
            if (msg.code == 201) {
                alert("评价成功");
                // evaluate.style.display="none";
            } else if (msg.code == 401) {
                alert("检测到敏感词！" + msg.desc + "请修改后再评价！")
            }
        },
        error: function (xhr) {
            // alert("死亡");
            // alert(xhr.status);
        }
    })
}

//监听评价里的叉
$("body").delegate(".icon-chaa", "click", function () {
    evaluate.style.display = "none";
});

//监听评价里的确认
$("body").delegate(".evaluateSure", "click", function () {
    massage = commentArea.value;
    stuEvaluation(evaluation, wKey, massage, formId);
    evaluate.style.display = "none";
    gerfinishOrder(page);
    // $(this).html("")
})
$("body").delegate(".bye", "click", function () {
    cancellation();
    window.location.href = "/login.html"
})

//上传文件
function onloadFile() {
    document.photo.submit();
}

//监听历史公告的叉
var historyNotice = document.getElementsByClassName("historyNotice")[0];
$("body").delegate(".history-cha", "click", function () {
    historyNotice.style.display = "none";
});
//监听公告点击
$("body").delegate(".history", "click", function () {
    historyNotice.style.display = "block";
});
//获得所有历史公告
var historyContant = document.getElementsByClassName("historyContant")[0];
$.ajax({
    type: "POST",
    url: "/student/history/board",
    dataType: "json",
    async: true,
    success: function (msg) {
        if (msg.code == 200) {
            var data = msg.data;
            for (var i = 0; i < msg.size; i++) {
                $(".historyContant").append('<div class="historyInside"></div>');
                $(".historyInside").eq(i).append('<div class="boardMsg">' + data[i].boardMsg + '</div>');
                $(".historyInside").eq(i).append('<div class="his-date">' + data[i].date + '</div>');
            }
        }
    },
    error: function (xhr) {
        // alert(xhr.status);
    }
})
//显示略所图
var input = document.getElementById("inputphoto");
// 当用户上传时触发事件
input.onchange = function () {
    readFile(this);
}
//处理图片并添加都dom中的函数
var readFile = function (obj) {
    // 获取input里面的文件组
    var fileList = obj.files;
    //对文件组进行遍历，可以到控制台打印出fileList去看看
    for (var i = 0; i < fileList.length; i++) {
        var reader = new FileReader();
        reader.readAsDataURL(fileList[i]);
        // 当文件读取成功时执行的函数
        reader.onload = function (e) {
            img = document.createElement('img');
            img.src = this.result;
            $(".saveImg").append(img);
        }
    }
}

//获得图片地址
function getPhoto(formId, position, element) {
    $.ajax({
        data: JSON.stringify({
            "formId": formId,
            "old": element
        }),
        type: "POST",
        dataType: "json",
        async: false,
        url: "/path.get",
        success: function (msg) {
            if (msg.code == 200) {
                var data = msg.data;
                if (data.size == 0) {
                    if (element == 0) {
                        $(".orderImg").eq(position).append('<img src="img/head1.jpg">');
                    } else {
                        $(".finishorderImg").eq(position).append('<img src="img/head1.jpg">');
                    }

                }
                for (var i = 0; i < data.size; i++) {
                    if (element == 0) {
                        $(".orderImg").eq(position).append('<img src="' + data.arr[i] + '">');
                    } else {
                        $(".finishorderImg").eq(position).append('<img src="' + data.arr[i] + '">');
                    }

                    break;
                }
            }
        },
        error: function (xhr) {
            // alert(xhr.status);
        }
    })
}

//注销方法
function cancellation() {
    $.ajax({
        type: "post",
        url: "/student/logout",
        success: function (msg) {

        },
        error: function (error) {

        }
    })
}
/*校验电话码格式 */
var judgeWrong=document.getElementsByClassName("judgeWrong")[0];
function isTelCode() {
    var str=stuPhone.value;
    var reg= /^[1](([3|5|8][\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\d]{8}$/;
	if(reg.test(str)){
        judgeWrong.style.display="none";
    }
    else{
        judgeWrong.style.display="block";
    }
}

/*校验邮件地址是否合法 */
function IsEmail(str) {
	var reg=/^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/;
	return reg.test(str);
}
//给进度查询中的确认完成按钮添加节流
var finishButton=document.getElementsByClassName("finish");
for(var i=0;i<finishButton.length;i++){
    finishButton[i].onclick=throttle(insureFinish,5000,1);
}
//给进度查询中的一键再修中的确认按钮添加节流
var againButton=document.getElementsByClassName("againInsure");
for(var i=0;i<againButton.length;i++){
    againButton[i].onclick=throttle(againRepair,5000,1);
}
//根据电话判断是否正确
