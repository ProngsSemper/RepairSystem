$(document).ready(function () {
    var bodyWidth = window.screen.width;
    var body = document.getElementsByTagName("body")[0];
    body.style.width = window.screen.width;
    var data = document.getElementsByClassName("dateTime")[0];
    var workerName=document.getElementsByClassName("left-tit")[0];
    workerName.innerText=decodeURI(getCookie("workerName"));
    var a = new Date();
    var day = a.getDate();
    var month = a.getMonth() + 1;
    var year = a.getFullYear();
    var b = new Array("日", "一", "二", "三", "四", "五", "六");
    var week = new Date().getDay();
    var str = " 星期" + b[week];
    var str1 = year + '年' + month + '月' + day + '日';
    data.innerText = str1 + str;
    // getMsg(1);
});
//监听返回按钮
var bigBox = document.getElementsByClassName("bigBox")[0];
var operatrContant = document.getElementsByClassName("operatrContant")[0];
var search = document.getElementsByClassName("search")[0];
var searchFlag = 0;
$("body").delegate('.returnLast', 'click', function () {
    bigBox.style.display = "block";
    operatrContant.style.display = "none";
    search.style.display = "block";
});
//监听处理按钮
$("body").delegate('.deal', 'click', function () {
    bigBox.style.display = "none";
    operatrContant.style.display = "block";
    search.style.display = "none";
    formId = $(this).parent().parent().attr("formid");
    getFormDetail(formId);
});
//监听左侧导航栏点击
searchFlag=0;
var navImg = document.getElementsByClassName("navImg");
var finish = document.getElementsByClassName("finish")[0];
navImg[0].onclick = function () {
    bigBox.style.display = "block";
    finish.style.display = "none";
    search.style.display = "block";
    $(".page").html("");
    searchFlag=0;
    getMsg(1)
}
navImg[1].onclick = function () {
    bigBox.style.display = "none";
    finish.style.display = "block";
    operatrContant.style.display = "none";
    search.style.display = "block";
    $(".page").html("");
    searchFlag=1;
    getWorkerFinishOrder(1)
}
//获得数据
getMsg(1);

function getMsg(pageCount) {
    $.ajax({
        type: "POST",
        url: "/worker/incomplete/form",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "page": pageCount,
            "limit": 10,
        }),
        success: function (msg) {
            var page = $(".page");
            $(".tableBox").html("");
            // $(".page").html("");
            var data = msg.data;
            var b = $('.page').children().length == 0;

            if (b) {
                page.append('<span class="page-number cur">' + 1 + '</span>');
                for (var i = 2; i <= msg.totalPage; i++) {
                    page.append('<span class="page-number">' + i + '</span>');
                }
            }

            $(".tableBox").append('<div class="grid-content bg-purple-dark">' + '<div class="formId">报修单号</div>' + '<div class="formNumber">学号</div>' + '<div class="adress">地址</div>' + '<div class="contant">内容</div>' + '<div class="operate">操作</div>' + '</div>');
            for (var i = 0; i < msg.size; i++) {
                $(".tableBox").append('<div class="grid-content"></div>');
                $(".grid-content").eq(i + 1).append('<div class="formId">' + data[i].formId + '</div>' +
                    '<div class="formNumber">' + data[i].stuId + '</div>' +
                    '<div class="adress">' + data[i].room + '</div>' +
                    '<div class="contant">' + data[i].formMsg + '</div>' +
                    '<div class="operate"><a href="javascript:;" class="deal">详情</a></div>')
                if (i % 2 == 0) {
                    $(".grid-content").eq(i + 1).addClass("bg-purple");
                } else {
                    $(".grid-content").eq(i + 1).addClass("bg-purple-light");
                }
                $(".grid-content").eq(i + 1).attr("formid", data[i].formId);
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

function finishButton(formId, stuMail) {
    $.ajax({
        type: "POST",
        url: "/worker/queryCode",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "queryCode": 2,
            "formId": formId,
            "stuMail": stuMail
        }),
        success: function (msg) {
            if (msg.code == 201) {
                alert("报修单已完成！");
                var bigBox = document.getElementsByClassName("bigBox")[0];
                var operatrContant = document.getElementsByClassName("operatrContant")[0];
                var search = document.getElementsByClassName("search")[0];
                bigBox.style.display = "block";
                operatrContant.style.display = "none";
                search.style.display = "block";
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    });
}

function wrongButton(formId, stuMail) {
    $.ajax({
        type: "POST",
        url: "/worker/queryCode",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "queryCode": -1,
            "formId": formId,
            "stuMail": stuMail
        }),
        success: function (msg) {
            if (msg.code == 201) {
                alert("报修单异常已处理！");
                var bigBox = document.getElementsByClassName("bigBox")[0];
                var operatrContant = document.getElementsByClassName("operatrContant")[0];
                var search = document.getElementsByClassName("search")[0];
                bigBox.style.display = "block";
                operatrContant.style.display = "none";
                search.style.display = "block";
            }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    });
}

function getFormDetail(formId) {

    $.ajax({
        type: "POST",
        url: "/worker/incomplete/formId",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "formId": formId,
        }),
        success: function (msg) {
            $(".orderInsideBox").html("");
            var data = msg.data;
            // let img = document.createElement('img');
            // $(img).attr("src", "img/jindutiao.png");
            $(".orderInsideBox").append('<p>报修人：' + data[0].stuName);
            $(".orderInsideBox").append('<p>报修电话：' + data[0].stuPhone);
            $(".orderInsideBox").append('<p>学号：' + data[0].stuId);
            $(".orderInsideBox").append('<p>学生邮箱：' + data[0].stuMail);
            $(".orderInsideBox").append('<p>地址：' + data[0].room);
            $(".orderInsideBox").append('<p>报修类型：' + data[0].wType);
            $(".orderInsideBox").append('<p>维修时间：' + data[0].appointDate + data[0].appointment + '点');
            $(".orderInsideBox").append('<p>报修内容：' + data[0].formMsg);
            $(".orderInsideBox").append('<p>图片：</p >');
            getPhoto(formId)
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

//监听点击完成按钮
$("body").delegate('.finishButton', 'click', function () {
    var stuMailBox = document.getElementsByClassName("orderInsideBox")[0];
    var stuMail = stuMailBox.getElementsByTagName("p")[3].innerText.split("：")[1];
    finishButton(formId, stuMail);
    getMsg(1);
});
//监听异常按钮
$("body").delegate('.wrongButton', 'click', function () {
    var stuMailBox = document.getElementsByClassName("orderInsideBox")[0];
    var stuMail = stuMailBox.getElementsByTagName("p")[3].innerText.split("：")[1];
    wrongButton(formId, stuMail);
    getMsg(1);
});

function searchFormId(formId) {
    $.ajax({
        type: "POST",
        url: "/worker/incomplete/formId",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "formId": formId,
        }),
        success: function (msg) {
            // if (msg.code == 200) {
                $(".tableBox").html("");
                var data = msg.data;
            $(".tableBox").append('<div class="grid-content bg-purple-dark">' + '<div class="formId">报修单号</div>' + '<div class="formNumber">学号</div>' + '<div class="adress">地址</div>' + '<div class="contant">内容</div>' + '<div class="operate">操作</div>' + '</div>');
            // for (var i = 0; i < msg.size; i++) {
                $(".tableBox").append('<div class="grid-content bg-purple"></div>');
                $(".grid-content").eq(1).append('<div class="formId">' + data[0].formId + '</div>' +
                    '<div class="formNumber">' + data[0].stuId + '</div>' +
                    '<div class="adress">' + data[0].room + '</div>' +
                    '<div class="contant">' + data[0].formMsg + '</div>' +
                    '<div class="operate"><a href="javascript:;" class="deal">详情</a></div>')
                    $(".grid-content").eq( 1).attr("formid", data[0].formId);
            // }
        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

function searchStuName(page) {
    var stuName = document.getElementsByClassName("searchInput")[0];
    
    $.ajax({
        type: "POST",
        url: "/worker/incomplete/stuName",
        dataType: "json",
        async: false,
        data: JSON.stringify({
            "stuName": stuName.value,
            "page": parseInt(page),
            "limit": 10,
        }),
        success: function (msg) {
            console.log(msg);
            // alert(msg.size);
            var page = $(".page");
            // $(".page").html("");
            $(".tableBox").html("");
            var data = msg.data;
            // console.log(data);
            var b = $('.page').children().length == 0;

            if (b) {
                page.append('<span class="page-number cur">' + 1 + '</span>');
                for (var i = 2; i <= msg.totalPage; i++) {
                    page.append('<span class="page-number">' + i + '</span>');
                }
            }

            $(".tableBox").append('<div class="grid-content bg-purple-dark">' +
                '<div class="formId">报修单号</div>' +
                '<div class="formNumber">学号</div>' +
                '<div class="adress">地址</div>' +
                '<div class="contant">内容</div>' +
                '<div class="operate">操作</div>' +
                '</div>')
            // alert(2);
            for (var i = 0; i < msg.size; i++) {
                $(".tableBox").append('<div class="grid-content"></div>');
                $(".grid-content").eq(i + 1).append('<div class="formId">' + data[i].formId + '</div>' +
                    '<div class="formNumber">' + data[i].stuId + '</div>' +
                    '<div class="adress">' + data[i].room + '</div>' +
                    '<div class="contant">' + data[i].formMsg + '</div>' +
                    '<div class="operate"><a href="javascript:;" class="deal">详情</a></div>')
                if (i % 2 == 0) {
                    $(".grid-content").eq(i + 1).addClass("bg-purple");
                } else {
                    $(".grid-content").eq(i + 1).addClass("bg-purple-light");
                }
                $(".grid-content").eq(i + 1).attr("formid", data[i].formId);
            }

        },
        error: function (xhr) {
            alert(xhr.status);
        }
    })
}

//监听搜索按钮
var searchContant = document.getElementById("searchContant");
var formIdInput = document.getElementsByClassName("searchInput")[0];
var returntable=document.getElementsByClassName("returntable");
$("body").delegate('.iconSearch', 'click', function () {
    if(bigBox.style.display=="block"){
        // alert("按钮已点击");
        if (searchContant.value == "报修单号") {
            // alert("按报修单号搜索");
            // alert(formIdInput.value);
            $(".page").html("");
            searchFormId(formIdInput.value);
            searchFlag = 2
            returntable[0].style.display="block";
        } else if (searchContant.value == "学生姓名") {
            // alert("按学生姓名搜索");
            $(".page").html("");
            searchStuName(1);
            searchFlag = 3;
            returntable[0].style.display="block";
        } else {
            alert("请选择搜索范围");
        }
    }
    else{
        if (searchContant.value == "报修单号") {
            // alert("按报修单号搜索");
            // alert(formIdInput.value);
            $(".page").html("");
            searchFinishFormId(formIdInput.value);
            searchFlag = 4
            returntable[1].style.display="block";
        } else if (searchContant.value == "学生姓名") {
            // alert("按学生姓名搜索");
            $(".page").html("");
            searchFinishstuName(formIdInput.value,1);
            searchFlag = 5;
            returntable[1].style.display="block";
        } else {
            alert("请选择搜索范围");
        }
    }
});
//监听点击页码操作
$("body").delegate(".page>span", "click", function () {
    var number = $(this).html();
    if (searchFlag == 0) {
        // $(".page").html("");
        getMsg(number);
    } 
    else if(searchFlag==1)
    {
        // $(".page").html("");
        getWorkerFinishOrder(number)
    }
    else if(searchFlag==2){
        
    }
    else if(searchFlag==3){
        // $(".page").html("");
        searchStuName(number);
    }
    else if(searchFlag==4)
    {

    }
    else if(searchFlag==5){
        // $(".page").html("");
        searchFinishstuName(formIdInput.value,number);
    }
    $(this).addClass("cur");
    $(this).siblings().removeClass("cur");
});

//监听评价按钮里的×
var historyNotice=document.getElementsByClassName("historyNotice")[0];
$("body").delegate(".history-cha", "click", function () {
    historyNotice.style.display="none";
});
//监听评价点击
$("body").delegate(".percentage", "click", function () {
    historyNotice.style.display="block";
});
//获得工人好评率
$.ajax({
    type:"POST",
    dataType:"json",
    url:"/worker/evaluation",
    success:function(msg){
        if(msg.code==200){
            $(".percentage").html(msg.data);
        }
    },    
    error:function(xhr){
        alert(xhr.status);
    }
})
//获得工人所有评价
$.ajax({
    type:"POST",
    dataType:"json",
    url:"/worker/evaluation/detail",
    success:function(msg){
        console.log(msg)
        if(msg.code==200){
            var data=msg.data;
            for(var i=0;i<data.length;i++){
                $(".historyContant").append('<div class="historyInside"></div>');
                $(".historyInside").eq(i).append('<div class="boardMsg">'+data[i].msg+'</div>');
            }
        }
    },    
    error:function(xhr){
        alert(xhr.status);
    }
})
//获得工人完成的数据
getWorkerFinishOrder(1);
function getWorkerFinishOrder(pageCount){
    $.ajax({
        type:"POST",
        dataType:"json",
        url:"/worker/complete/form",
        data:JSON.stringify({
            "page":pageCount,
            "limit":10,
        }),
        success:function(msg){
            if(msg.code==200){
            var data=msg.data;
            // alert(msg.size);
            var page = $(".page");
            // $(".page").html("");
            $(".finishtableBox").html("");
            var data = msg.data;
            console.log(data);
            var b = $('.page').children().length == 0;

            if (b) {
                page.append('<span class="page-number cur">' + 1 + '</span>');
                for (var i = 2; i <= msg.totalPage; i++) {
                    page.append('<span class="page-number">' + i + '</span>');
                }
            }

            $(".finishtableBox").append('<div class="finishgrid-content bg-purple-dark">' +
                '<div class="formId">报修单号</div>' +
                '<div class="formNumber">学号</div>' +
                '<div class="adress">地址</div>' +
                '<div class="contant">内容</div>' +
                '<div class="operate">状态</div>' +
                '</div>')
            // alert(2);
            for (var i = 0; i < msg.size; i++) {
                $(".finishtableBox").append('<div class="finishgrid-content"></div>');
                $(".finishgrid-content").eq(i + 1).append('<div class="formId">' + data[i].formId + '</div>' +
                    '<div class="formNumber">' + data[i].stuId + '</div>' +
                    '<div class="adress">' + data[i].room + '</div>' +
                    '<div class="contant">' + data[i].formMsg + '</div>' + '<div class="operate">已完成</div>')
                if (i % 2 == 0) {
                    $(".finishgrid-content").eq(i + 1).addClass("bg-purple");
                } else {
                    $(".finishgrid-content").eq(i + 1).addClass("bg-purple-light");
                }
                $(".finishgrid-content").eq(i + 1).attr("formid", data[i].formId);
            }

            }
        },    
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//根据学生姓名查询已完成
function searchFinishstuName(stuName,pageCount){
    $.ajax({
        type:"POST",
        dataType:"json",
        url:"/worker/complete/stuName",
        data:JSON.stringify({
            "page":pageCount,
            "limit":10,
            "stuName":stuName
        }),
        success:function(msg){
            if(msg.code==200){
            var data=msg.data;
            // alert(msg.size);
            var page = $(".page");
            // $(".page").html("");
            $(".finishtableBox").html("");
            var data = msg.data;
            console.log(data);
            var b = $('.page').children().length == 0;

            if (b) {
                page.append('<span class="page-number cur">' + 1 + '</span>');
                for (var i = 2; i <= msg.totalPage; i++) {
                    page.append('<span class="page-number">' + i + '</span>');
                }
            }

            $(".finishtableBox").append('<div class="finishgrid-content bg-purple-dark">' +
                '<div class="formId">报修单号</div>' +
                '<div class="formNumber">学号</div>' +
                '<div class="adress">地址</div>' +
                '<div class="contant">内容</div>' +
                '<div class="operate">状态</div>' +
                '</div>')
            // alert(2);
            for (var i = 0; i < msg.size; i++) {
                $(".finishtableBox").append('<div class="finishgrid-content"></div>');
                $(".finishgrid-content").eq(i + 1).append('<div class="formId">' + data[i].formId + '</div>' +
                    '<div class="formNumber">' + data[i].stuId + '</div>' +
                    '<div class="adress">' + data[i].room + '</div>' +
                    '<div class="contant">' + data[i].formMsg + '</div>' + '<div class="operate">已完成</div>')
                if (i % 2 == 0) {
                    $(".finishgrid-content").eq(i + 1).addClass("bg-purple");
                } else {
                    $(".finishgrid-content").eq(i + 1).addClass("bg-purple-light");
                }
                $(".finishgrid-content").eq(i + 1).attr("formid", data[i].formId);
            }

            }
        },    
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//根据formId查询已完成
function searchFinishFormId(formId){
    $.ajax({
        type:"POST",
        dataType:"json",
        url:"/worker/complete/formId",
        data:JSON.stringify({
            "formId":formId,
        }),
        success:function(msg){
            if(msg.code==200){
            var data=msg.data;
            // alert(msg.size);
            var page = $(".page");
            $(".page").html("");
            $(".finishtableBox").html("");
            var data = msg.data;
            // console.log(data);
            $(".finishtableBox").append('<div class="finishgrid-content bg-purple-dark">' +
                '<div class="formId">报修单号</div>' +
                '<div class="formNumber">学号</div>' +
                '<div class="adress">地址</div>' +
                '<div class="contant">内容</div>' +
                '<div class="operate">状态</div>' +
                '</div>')
            // alert(2);
            // for (var i = 0; i < msg.size; i++) {
                $(".finishtableBox").append('<div class="finishgrid-content"></div>');
                $(".finishgrid-content").eq(1).append('<div class="formId">' + data[0].formId + '</div>' +
                    '<div class="formNumber">' + data[0].stuId + '</div>' +
                    '<div class="adress">' + data[0].room + '</div>' +
                    '<div class="contant">' + data[0].formMsg + '</div>' + '<div class="operate">已完成</div>')
                // if (i % 2 == 0) {
                    $(".finishgrid-content").eq(1).addClass("bg-purple");
                // } else {
                //     $(".finishgrid-content").eq(i + 1).addClass("bg-purple-light");
                // }
                $(".finishgrid-content").eq(1).attr("formid", data[0].formId);
            // }

            }
        },    
        error:function(xhr){
            alert(xhr.status);
        }
    })
}
//获得图片地址
function getPhoto(formId){
    $.ajax({
        data:JSON.stringify({
            "formId":formId
        }),
        type:"POST",
        dataType:"json",
        async:false,
        url:"/path.get",
        success:function(msg){
            if (msg.code==200) {
                var data=msg.data;
                for(var i=0;i<data.size;i++){
                    $(".orderInsideBox").append('<img src="'+data.arr[i]+'">');
                }
            }            
        },
        error:function(xhr){
            // alert(xhr.status);
        }
    })
}
//监听搜索页面的返回
$("body").delegate(".returntable","click",function(){
    if(bigBox.style.display=="block"){
        returntable[0].style.display="none";
        $(".page").html("");
        searchFlag=0;
        getMsg(1);
    }
    else{
        returntable[1].style.display="none";
        $(".page").html("");
        searchFlag=1;
        getWorkerFinishOrder(1);
    }
})
//注销方法
function cancellation(){
    $.ajax({
        type:"post",
        url:"/worker/logout",
        success:function(msg){

        },
        error:function(error){

        }
    })
}
//监听注销按钮
$("body").delegate(".bye","click",function(){
    cancellation();
    window.location.href="/login.html";
})