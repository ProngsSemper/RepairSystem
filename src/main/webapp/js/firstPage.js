//切换
var li=document.getElementsByClassName('index');
var contant=document.getElementsByClassName("contant");
for(var i=0;i<li.length;i++){
    li[i].index=i;
    li[i].onclick=function(){
        for(var i=0;i<li.length;i++){
            li[i].className="index";
            contant[i].style.display="none";
        }
        li[this.index].classList.add("current");
        contant[this.index].style.display="block";
    }
}
//提交表单信息
var stuId=document.getElementsByClassName("repair-information")[2];
var stuName=document.getElementsByClassName("repair-information")[0];
var stuPhone=document.getElementsByClassName("repair-information")[1];
var stuMail=document.getElementsByClassName("repair-information")[3];
var formMsg=document.getElementsByClassName("textareaStyle")[0];
var photoId="";

var radio=document.getElementsByClassName("radios");

// var wTypr=$(".radios").val();
var appointment=document.getElementsByClassName("appoint")[0];
var domiNumber=document.getElementsByClassName("dormitoryNumber")[0];
var adress=document.getElementById("adress");
var buliding=document.getElementById("buliding");
$("body").delegate(".radios","click",function(){
    for(var i=0;i<radio.length;i++){
        if(radio[i].checked){
            wType=radio[i].value;
            break;
        }
    }
})
var button=$(".handin-tit");
$("body").delegate(".handin-tit","click",function(){
    $.ajax({
        type:"POST",
        url: "/student/submission/form",
        dataType: "json",
        data: JSON.stringify({
            "stuId":stuId.value,
            "stuName":stuName.value,
            "stuPhone":stuPhone.value,
            "stuMail":stuMail.value,
            "formMsg":formMsg.value,
            "photoId":"",
            "wType":wType,
            "room":adress.value+buliding.value+domiNumber.value,
            "appointment":appointment.value
        }),
        success:function(msg){
            if(msg.code==200){
                alert("数据发送成功");
            }
        },
        error:function(xhr){
            alert(xhr.status);
        }
    })
})