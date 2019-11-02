//监听返回
$("body").delegate(".return","click",function(){
    window.location.href='/managerFirstPage.html';
})
//监听确认按钮
var textArea=document.getElementsByClassName("textArea")[0];
$("body").delegate(".sure","click",function(){
    var judge=confirm("确认修改公告");
    var board=textArea.value;
    if(judge){
        sendNotice(board);
    }
})
//发送公告
function sendNotice(board){
    $.ajax({
        type: "POST",
        url: "/admin/board",
        dataType: "json",
        data: JSON.stringify({
            "board":board

        }),
        success: function(msg){
            if(msg.code==200){
                alert("发布成功");
            }else if (msg.code==401){
                alert("发布失败！内容不能为空！");
            }else if(msg.code==400){
                alert("检测到敏感词！"+msg.desc+"请修改后再评价！");
            }
        },
        errro:function(xhr){
            alert(xhr.status);
        }
    })
}