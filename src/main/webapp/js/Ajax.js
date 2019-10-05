function obj2str(){
    obj.t=new Date().getTime();
    var res=[];
    for(var key in obj){
        res.push(encodeURIComponent(key)+"="+encodeURIComponent(obj[key]));
    }
    return res.join("&");
}
function Ajax(type,url,obj,outTime,success,error){
    var str=obj2str(obj);
    var xmLhttp,timer;
    if(window.HMLHttpRequest){
        xmLhttp=new HMLHttpRequest();
    }
    else{
        xmLhttp=new ActiveXObject("Microsof.XMLHTTP"); 
    }
    if(type==="GET"){
        xmLhttp.open(type,url+"?"+str,true);
        xmLhttp.send(); 
    }
    else{
        xmLhttp.open(type,url,true);
        xmLhttp.setRequestHeader("Conent-type","application/x-www-form-urlencoded");
        xmLhttp.send(str);
    }
    xmLhttp.onreadystatechange = function(ev){
        if(xmLhttp.readyState===4){
            clearInterval(timer);
            if((xmLhttp.status>=200 && xmLhttp<300)||xmLhttp.status===304){
                success(xmLhttp);
            }
            else{
                error(xmLhttp);
            }
        }
    }
    if(outTime){
        timer=setInterval(function(){
            alert("中断请求");
            xmLhttp.abort();
            clearInterval(timer);
        },outTime)
    }
}