var lockReconnect = false;
var ws = null;
var sender = null;
var onlineList = null;
var isAdmin = false;
var wsCanOpen = true;
var userName = null;
var unread_count=0;
var recent_img_path = null;

var type={

    "talk":200,
    "updateList":202,
    "self_info":207,
    "offline":409,
    "count":1,
    "page":0,
    "count_info_unread":2,
    "img":3
};
//心跳包：发送一个空串过去，尽量减少后台损耗
var ping = "";

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
var connectCount=0;
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
            if(++connectCount>20)
            {
                window.location.href="index.do?a="+new Date();
            }

        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket(url);
        } else {
            alert("您的浏览器不支持websocket")
        }

        initEventHandle();
    } catch (e) {
        if(connectCount>20)
        {
            wsCanOpen=false;
            window.location.href="index.do?a="+new Date();
            return;
        }
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
        if(tmp==null||tmp.length<=2)
        {
            return;
            //发送的ping包，后台给回响应
        }
        var obj = getJsonObject(tmp);
        console.log(tmp);
        // let str = obj.msg.replace("script","***");



        let command = obj.type;
        switch (command) {
            case type.talk:{
                console.log("聊天事务: ");
                fillWhite(obj.sender+": "+obj.msg+"\r\n"+getCurTime());
                break;
            }
            case type.self_info:{
                sender = obj.sender;
                userName = sender;
                isAdmin = obj.isAdmin;
                if(obj.onlineList!=null)
                {
                    console.log("聊天集合： "+onlineList);
                    // alert(onlineList);
                    onlineList = obj.onlineList;
                    updateOnlineList(onlineList);
                }
                console.log("sender: "+sender);
                break;
            }
            case type.updateList:{
                //某人上线连接管理员通道，管理员前端页面展示学生在线人数，同时学生页面也要展示管理员在线人数
                console.log("更新聊天列表"+obj.onlineList);
                onlineList = obj.onlineList;
                console.log(onlineList);
                // alert(onlineList);
                updateOnlineList(onlineList);
                break;
            }

            case type.offline:{
                console.log(obj);
                if(obj.onlineList!=null)
                {
                    onlineList = obj.onlineList;
                    updateOnlineList(onlineList);
                }
                break;
            }
            case type.count:{
                //获取分页数
                let count = obj.totalCount;
                alert(count);
                break;

            }
            case type.page:{

                // alert(obj);
                let list = obj.messageList;
                $(".contant").find(".history").remove();
                // data=obj.messageList;
                for(var i=0;i<list.length;i++){
                    if(list[i].sender===sender)
                    {
                        if(list[i].msg.startsWith("http:/"))
                        {
                            let img = createImg(list[i].msg);
                            img.style.width = '210px';
                            let line = getElement("div","line");
                            let p = document.createElement("p");
                            p.innerHTML = list[i].sender+":";
                            p.style.color = "rgb(25, 68, 235)";
                            $(line).append(p);
                            $(line).append(img);
                            $(line).css("text-align","right")
                            $(".contant").prepend(line);

                            continue;
                        }

                        // alert(list[i].sender==sender);
                        fillGreenBefore(list[i].sender+": \r\n"+$.trim(list[i].msg)+"\r\n"+list[i].time);
                        continue;
                    }
                    // alert(list[i].sender);
                    if(list[i].msg.startsWith("http:/"))
                    {
                        let img = createImg(list[i].msg);
                        // alert(img);
                        img.style.width="210px";
                        let line = getElement("div","line");
                        let p = document.createElement("p");
                        p.innerHTML = list[i].sender+":";
                        p.style.color = "#d07272";
                        $(line).append(p);
                        $(line).append(img);
                        $(".contant").prepend(line);
                        continue;
                    }

                    fillWhiteBefore(list[i].sender+": \r\n"+$.trim(list[i].msg)+"\r\n"+list[i].time);


                }

                $(".contant").prepend('<a href="javascript:;" class="history">获取历史消息记录</a>');


                // alert(list);
                break;
            }

            /*
            *
            * 您有新的 N 条记录，在这里获取数据
            * */
            case type.count_info_unread:{
                let count = obj.infoCount;
                unread_count = count;
                alert(count);
                break;
            }

            case type.img:{

                //接收到对方发来图片消息

                let img = createImg(obj.msg);
                img.style.width = '210px';
                let line = getElement("div","line");
                let p = document.createElement("p");
                p.innerHTML = obj.sender+":";
                p.style.color = "rgb(25, 68, 235)";
                $(line).append(p);
                $(line).append(img);
                $(".contant").append(line);

            }

            default:{
                console.log("其他事务");
            }


        }




    };
    ws.onclose = function () {
        reconnect();
        console.log("重新连接聊天室");
    };
    ws.onerror = function () {

        reconnect();
        wsCanOpen = false;
        console.log("连接出现错误，尝试重新连接");
    };




}

window.onbeforeunload = function () {
    ws.close();
    //退出窗口，先关闭socket
};

function reconnect() {
    if(!wsCanOpen) return;//服务器检测到身份异常，需要用户重新登录
    if(lockReconnect)return;
    lockReconnect = true;
    setTimeout(function () {
        fillWhite("聊天小助手："+" 检测到断线，正在重新连接...");
        createWebSocket();
        lockReconnect = false;
    },4000);
}

var heartCheck = {
    timeOut:60000,  //每8秒心跳检测
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
            ws.send(ping);
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




$(document).ready(function () {


});
function updateOnlineList(arr) {
    $("#list").find("option").remove();
    var template = $("#list");
    if(isAdmin)
    {
        //管理员可以通知所有人，学生不能通知所有管理员
        template.append('<option>'+"所有人"+'</option>');
        // $('#sender-name').val('所有人');
    }
    if(arr==null)
    {
        return;
    }
    for(var i in arr)
    {
        console.log(arr);
        template.append('<option>'+arr[i]+'</option>');
    }
    if(!isAdmin)
    {
        if(arr!=null&&arr.length>0)
        {
            //随机获取一位管理员聊天
            $('#receiver-name').val(arr[Math.floor((Math.random()*arr.length))]);
        }else{
            $('#receiver-name').val('离线留言');
            template.append('<option>'+"离线留言"+'</option>')

        }

    }
}


function getPageCount() {
    //发送获取消息请求
    var msgPacker = {"type":type.count};
    ws.send(JSON.stringify(msgPacker));

}
function getPageMessage() {
    //发送获取具体某一页的请求
    var msgPacker = {
        "type":type.page,
        "page":1,
        "size":10
    };
    //我要看第一页，10条数据
    ws.send(JSON.stringify(msgPacker));
}


function test_get_unread_count() {
    let request = {
        type:type.count_info_unread
    };
    ws.send(JSON.stringify(request));
}


//返回一个 js对象
function createImg(path) {
    let img = document.createElement("img");
    img.src = path;
    return img;
}

function getElement(name,className) {
    let tag = document.createElement(name);
    tag.classList.add(className);
    return tag;
}


//在聊天框写入img图片
function addLastImg(path) {
    if(path==null)
    {
        return;
    }
    let img = createImg(path);
    img.style.width = '210px';
    $(img).css("float","right");
    let line = getElement("div","line");

    $(line).append(img);
    $(".contant").append(line);

}


