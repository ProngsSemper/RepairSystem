var lockReconnect = false;
var ws = null;

var type={
    "ping":0,
    "talk":200
};
var ping = {
    "type":type.ping
};

$(document).ready(function () {

    $('#talk').click(function () {
        window.location.href = "communication.html?a=1";
    });

    $("#back").click(
        function () {
            window.location.href ="index.do?a="+new Date();
        }
    );
    createWebSocket();


});


function getBasePath() {
    let location = (window.location+'').split('/');
    return location[0] + '//' + location[2] + '/';
}

function getBasePath2() {
    let location = (window.location+'').split('/');
    return  location[2] + '/';
}


function createWebSocket() {
    var url_t = getBasePath2();
    var url = "ws://"+url_t+"chat";
    // alert(url);
    try {
        if ('WebSocket' in window) {
            ws = new WebSocket(url);
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(url);
        } else {
            alert("您的浏览器不支持websocket")
        }
        initEventHandle();
    } catch (e) {
        reconnect(url);
        console.log(e);
    }


}

function initEventHandle() {
    ws.onopen = function () {
        heartCheck.reset().go();
        console.log("socket连接成功！"+new Date());
        fillGreen("欢迎来到聊天室");

    };
    ws.onmessage = function (event) {
        heartCheck.reset().go();
        var tmp = event.data;
        var obj = eval('('+tmp+')');
        // let str = obj.msg.replace("script","***");
        if(obj.msg!=undefined)
        {
            fillWhite(obj.sender +":\r\n"+obj.msg);
        }



    };
    ws.onclose = function () {
        reconnect();
        console.log("重新连接聊天室");
    };
    ws.onerror = function () {
        reconnect();
        console.log("连接出现错误，尝试重新连接");
    };




}

window.onbeforeunload = function () {
    ws.close();
    //退出窗口，先关闭socket
};

function reconnect() {
    if(lockReconnect)return;
    lockReconnect = true;
    setTimeout(function () {
        createWebSocket();
        lockReconnect = false;
    },2000);
}

var heartCheck = {
    timeOut:6000,  //每6心跳检测
    timeOutObj:null,
    serverTimeOutObj:null,
    reset:function () {
        clearTimeout(this.timeOutObj);
        clearTimeout(this.serverTimeOutObj);
        return this;
    },
    go:function () {
        var self = this;
        this.timeOutObj = setTimeout(function () {
            ws.send(JSON.stringify(ping));
            //发送心跳包，然后倒计时检测是否有回复
            console.log("ping ! ");
            self.serverTimeOutObj = setTimeout(function () {
                console.log("try=close");
                ws.close();
                //关掉 socket，然后重新连接聊天室
            },self.timeOut);
        },this.timeOut)
    }
};










