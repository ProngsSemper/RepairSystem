var bodyWidth=window.screen.width;
var body=document.getElementsByTagName("body")[0];
body.style.width=window.screen.width;
var data=document.getElementsByClassName("date")[0];
var a=new Date();
var day=a.getDate();
var month=a.getMonth()+1;
var year=a.getFullYear();
var b = new Array("日", "一", "二", "三", "四", "五", "六");
var week = new Date().getDay();
var str = " 星期"+ b[week];
var str1=year+'年'+month+'月'+day+'日';
data.innerText=str1+str;
// // 这是我写的样例测试,ajax可行后记得注释
function addData(msg){
    var obj=msg;
    var data=msg.data;
    var table=$(".repairItem");
    var pageNumber=Math.ceil(data.totalCount/10);
    var page=$(".page");
    for(var i=1;i<=pageNumber;i++){
        if(i==1){
            continue;
        }
        else{
            page.append('<span class="page-number">'+i+'</span>');
        }
    }
    $("body").delegate(".page>span","click",function(){
        $(this).addClass("cur");
        $(this).siblings().removeClass("cur");
        var number=$(this).html();
        getData(number);
    })
    if(data.length>10){
        for(var i=0;i<10;i++){
            var row=$('<tr class="row"></tr>');
            var colNumber=$('<td class="col">'+data[i].stuId+'</td>')
            var colAdress=$('<td class="col">南苑</td>')
            var colContant=$('<td class="col special">水管坏了</td>')
            var colOperate=$('<td class="col"><a href="javascript:;" class="deal">处理</a><a href="javascript:;" class="del">删除</a></td>')
            table.append(row);
            row.append(colNumber,colAdress,colContant,colOperate);
        }
    }
    else{
        for(var i=0;i<data.length;i++){
            var row=$('<tr class="row"></tr>');
            var colNumber=$('<td class="col">'+data[i].stuId+'</td>')
            var colAdress=$('<td class="col">南苑</td>')
            var colContant=$('<td class="col special">水管坏了</td>')
            var colOperate=$('<td class="col"><a href="javascript:;" class="deal">处理</a><a href="javascript:;" class="del">删除</a></td>')
            table.append(row);
            row.append(colNumber,colAdress,colContant,colOperate);
        }
    }
    function getData(page){
        $(".repairItem").html("");
        $(".repairItem").append('<tr class="row "><td class="col">学号</td><td class="col">地址</td><td class="col">内容</td><td class="col">操作</td></tr>')
        if(page==pageNumber){
            for(var i=(page-1)*10;i<data.length;i++){
                var row=$('<tr class="row"></tr>');
                var colNumber=$('<td class="col">'+data[i].stuId+'</td>')
                var colAdress=$('<td class="col">南苑</td>')
                var colContant=$('<td class="col special">水管坏了</td>')
                var colOperate=$('<td class="col"><a href="javascript:;" class="deal">处理</a><a href="javascript:;" class="del">删除</a></td>')
                table.append(row);
                row.append(colNumber,colAdress,colContant,colOperate);
            }
        }
        else{
            for(var i=page-1;i<(page*10);i++){
                var row=$('<tr class="row"></tr>');
                var colNumber=$('<td class="col">'+data[i].stuId+'</td>')
                var colAdress=$('<td class="col">南苑</td>')
                var colContant=$('<td class="col special">水管坏了</td>')
                var colOperate=$('<td class="col"><a href="javascript:;" class="deal">处理</a><a href="javascript:;" class="del">删除</a></td>')
                table.append(row);
                row.append(colNumber,colAdress,colContant,colOperate);
            }
        }
    }
}
