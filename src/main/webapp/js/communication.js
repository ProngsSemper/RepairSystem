//获得发送数据
var sectArea=document.getElementsByClassName("seatArea")[0];
var button=document.getElementsByClassName("stuSend")[0];

//监听发送按钮点击事件
$("body").delegate(".stuSend","click",function(){
    sendMsg();

});

function launch() {
    $(".contant").append('<div class="line"><div class="bg-green own">'+html_encode(html_decode(sectArea.value))+'</div></div>');
    sectArea.value="";
}


function receiveMsg(msg) {
    $(".contant").append('<div class="line"><div class="bg-white other">'+html_encode(html_decode(sectArea.value))+'</div></div>');
}
function html_encode(str) 
{ 
    var s = ""; 
    if (str==undefined||str.length == 0) return "";
    s = str.replace(/&/g, "&amp;"); 
    s = s.replace(/</g, "&lt;"); 
    s = s.replace(/>/g, "&gt;"); 
    s = s.replace(/ /g, "&nbsp;"); 
    s = s.replace(/\'/g, "&#39;"); 
    s = s.replace(/\"/g, "&quot;"); 
        s = s.replace(/\n/g, "<br/>"); 
    return s; 
} 

function html_decode(str) 
{ 
    var s = ""; 
    if (str==undefined||str.length == 0) return "..";
    s = str.replace(/&amp;/g, "&"); 
    s = s.replace(/&lt;/g, "<"); 
    s = s.replace(/&gt;/g, ">"); 
    s = s.replace(/&nbsp;/g, " "); 
    s = s.replace(/&#39;/g, "\'"); 
    s = s.replace(/&quot;/g, "\""); 
    s = s.replace(/<br\/>/g, "\n"); 
    return s; 
}




/**
 * @author lyr
 *
 * */
function fillGreen(msg) {
    var line = $(".line:eq(0)").clone();
    var child =line.children();


    child.html(to_string(msg));
    line.append(child);


    $(".contant").append(line);
    down();

}

function fillWhite(msg) {
    // alert(msg);
    // alert(msg.msg);
    var line = $(".line:eq(1)").clone();
    var child =line.children();

    child.html(to_string(msg));
    line.append(child);


    $(".contant").append(line);
    down();
}


function to_string(str)
{
    return html_encode(html_decode(str));
}




function getJsonObject(rawText) {
    return eval('('+rawText+')');

}

function sendMsg() {
    let receiver_name = $("#receiver-name").val();
    if(receiver_name==null||receiver_name==''||receiver_name.length<2)
    {
        alert("请输入发送对象");
        return;
    }
    var msg = {
        "msg":$("#msg1").val(),//消息款的信息
        // "target":$("#target").val()
        "type":type.talk,
        "sender":sender,
        "target":receiver_name
    };
    if(msg.msg.replace(/\s/g,"")==="")
    {
        return;
    }

    var pack = JSON.stringify(msg);
    ws.send(pack);
    fillGreen(""+msg.msg);
    // fillWhite(msg);
    sectArea.value="";

}


function closeWebSocket() {
    fillWhite("对不起，管理员暂时不在线");
    ws.close();
}

function test_send(sender,target,msg) {
    var pack = {
        "sender":sender,
        "target":$("#receiver-name").html(),
        "msg":$.trim(msg)
    };
    ws.send(JSON.stringify(pack));

}



function down() {
    var textarea = document.getElementById("window-talk");
    textarea.scrollTop = textarea.scrollHeight;
}


function getCurTime() {
    var date=new Date();

    var year=date.getFullYear();
    var month=date.getMonth();
    var day=date.getDate();

    var hour=date.getHours();
    var minute=date.getMinutes();
    var second=date.getSeconds();

    //这样写显示时间在1~9会挤占空间；所以要在1~9的数字前补零;
    if (hour<10) {
        hour='0'+hour;
    }
    if (minute<10) {
        minute='0'+minute;
    }


    var time=year+'/'+month+'/'+day+'/'+hour+':'+minute;
    return time;

}
//历史聊天记录
var page=1;
//监听超链接点击
$("body").delegate(".history","click",function () {
    $(this).remove();
    // $(".contant").prepend('<div class="line"><dic class="bg-green own">test</div></div>');
    getHistory(page);
    $(".contant").prepend('<a href="javascript:;" class="history">获取历史消息记录</a>');
    page++;
});

function getHistory(page){
    // $.ajax({
    //     type: "POST",
    //     url: "",
    //     data: JSON.stringify({
    //         "type":0,
    //         "page":page,
    //         "size":10,
    //
    //     }),
    //     dataType: "json",
    //     success: function (msg) {
    //         data=msg.messageList;
    //         for(var i=0;i<msg.size;i++){
    //             $(".contant").prepend('<div class="line"><dic class="bg-white other">'+data[i].msg+'</div></div>');
    //         }
    //     },
    //     error:function(xhr){
    //         alert(xhr.status);
    //     }
    // });

    var pack = JSON.stringify({
                 "type":type.page,
                 "page":page,
                 "size":5
    });
    ws.send(pack);



}
//获取窗口高度并查找离线记录
var contant=document.getElementById("window-talk");
contant.onscroll=function(){
    if(contant.scrollTop==0){
        $(".history").remove();
        // $(".contant").prepend('<div class="line"><dic class="bg-green own">test</div></div>');
        getHistory(page);
        $(".contant").prepend('<a href="javascript:;" class="history">获取历史消息记录</a>');
        page++;
    }
}