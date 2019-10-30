//获得发送数据
var sectArea=document.getElementsByClassName("seatArea")[0];
var button=document.getElementsByClassName("stuSend")[0]
//监听发送按钮点击事件
$("body").delegate(".stuSend","click",function(){
    launch();
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
    if (str.length == 0) return ""; 
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
    if (str.length == 0) return ""; 
    s = str.replace(/&amp;/g, "&"); 
    s = s.replace(/&lt;/g, "<"); 
    s = s.replace(/&gt;/g, ">"); 
    s = s.replace(/&nbsp;/g, " "); 
    s = s.replace(/&#39;/g, "\'"); 
    s = s.replace(/&quot;/g, "\""); 
    s = s.replace(/<br\/>/g, "\n"); 
    return s; 
} 


 
    console.log(html_decode('&lt;div&gt;123&lt;/div&gt;')); 
    console.log();


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
    };
    ws.onmessage=function (event) {
        alert("收到消息");
        alert(event.data);
        receiveMsg(event.data);

    };
    ws.onclose=function () {
        alert("socket 关闭");
    };

    $("#btn").click(function () {
        var msg = {
            "msg":$("#msg1").val(),//消息款的信息
            "sender":$("#sender").val(),
            "target":$("#target").val()
        };
        alert(msg.msg);
        var pack = JSON.stringify(msg);
        ws.send(pack);
    });
});















