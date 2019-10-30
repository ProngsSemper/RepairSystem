//获得发送数据
var sectArea=document.getElementsByClassName("seatArea")[0];
var button=document.getElementsByClassName("stuSend")[0]
//监听发送按钮点击事件
$("body").delegate(".stuSend","click",function(){
    sendMsg();
    // launch();
    // fillGreen("44ddddddddddddd44");
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

}

function fillWhite(msg) {
    // alert(msg);
    // alert(msg.msg);
    var line = $(".line:eq(1)").clone();
    var child =line.children();

    // var obj = eval('('+msg+')');
    //转化 为 json字符串
    child.html(to_string(msg));
    line.append(child);


    $(".contant").append(line);
}


function to_string(str)
{
    return html_encode(html_decode(str));
}



//开启webSocket


$(document).ready(function () {
    var url_t = getBasePath2();
    var url = "ws://"+url_t+"chat";
    // alert(url);
    ws = new WebSocket(url);
    ws.onerror = function () {
        alert("出现错误");
    };
    ws.onopen=function () {
        alert("开启聊天");
        fillGreen("欢迎来到聊天室");
    };
    ws.onmessage=function (event) {
        alert(event.data.msg);
        var tmp = event.data;
        var obj = eval('('+tmp+')');
        // let str = obj.msg.replace("script","***");
        fillWhite(obj.msg);

    };
    ws.onclose=function () {
        alert("无管理员在线,socket 关闭");
    };


});

function sendMsg() {
    var msg = {
        "msg":$("#msg1").val(),//消息款的信息
        // "target":$("#target").val()
    };

    // alert(msg.msg);
    // fillGreen(123);
    // fillGreen("dddddddddddddd");
    var pack = JSON.stringify(msg);
    ws.send(pack);
    fillGreen("我: "+msg.msg);
    // fillWhite(msg);
    sectArea.value="";

}













