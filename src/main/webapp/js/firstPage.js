//切换
var li = document.getElementsByClassName('index');
var contant = document.getElementsByClassName("contant");
for (var i = 0; i < li.length; i++) {
    li[i].index = i;
    li[i].onclick = function () {
        for (var i = 0; i < li.length; i++) {
            li[i].className = "index";
            contant[i].style.display = "none";
        }
        li[this.index].classList.add("current");
        contant[this.index].style.display = "block";
    }
}
//提交表单信息
var stuId = document.getElementsByClassName("repair-information")[2];
var stuName = document.getElementsByClassName("repair-information")[0];
var stuPhone = document.getElementsByClassName("repair-information")[1];
var stuMail = document.getElementsByClassName("repair-information")[3];
var formMsg = document.getElementsByClassName("textareaStyle")[0];
var photoId = "";
var a = new Date();
var y = a.getFullYear();
var day = a.getDate();
var months = a.getMonth() + 1;
// var year=a.getFullYear();
var radio = document.getElementsByClassName("radios");
var month = document.getElementById("month");
var days = document.getElementById("days");
var times = document.getElementById("times");
// var wTypr=$(".radios").val();
var appointment = document.getElementsByClassName("appoint")[0];
var domiNumber = document.getElementsByClassName("dormitoryNumber")[0];
var adress = document.getElementById("adress");
var buliding = document.getElementById("buliding");
$("body").delegate(".radios", "click", function () {
    for (var i = 0; i < radio.length; i++) {
        if (radio[i].checked) {
            wType = radio[i].value;
            break;
        }
    }
})
$("#month").append('<option value=' + months + '>' + months + '月' + '</option>');
$("#days").append('<option value=' + day + '>' + day + '日' + '</option>');
$("#days").append('<option value=' + (day + 1) + '>' + (day + 1) + '日' + '</option>');
$("#days").append('<option value=' + (day + 2) + '>' + (day + 2) + '日' + '</option>');
$("#days").append('<option value=' + (day + 3) + '>' + (day + 3) + '日' + '</option>');
$("#days").append('<option value=' + (day + 4) + '>' + (day + 4) + '日' + '</option>');
$("#days").append('<option value=' + (day + 5) + '>' + (day + 5) + '日' + '</option>');
$("#days").append('<option value=' + (day + 6) + '>' + (day + 6) + '日' + '</option>');


var button = $(".handin-tit");
$("body").delegate(".handin-tit", "click", function () {
    judege = confirm("确认提交");
    //不验证无法发送成功
    var b = address.value&&stuId.value&&stuName.value&&stuPhone.value&&stuMail.value&&formMsg.value&&wType.value&&times.value&&days.value&&month.value&&domiNumber.value&&buliding.value;
    if(b)
    {
        alert("请填写完整且正确的表单信息");
        return;
    }
    if (judege) {
        $.ajax({
            type: "POST",
            url: "/student/submission/form",
            dataType: "json",
            data: JSON.stringify({
                "stuId": stuId.value,
                "stuName": stuName.value,
                "stuPhone": stuPhone.value,
                "stuMail": stuMail.value,
                "formMsg": formMsg.value,
                "photoId": "",
                "wType": wType,
                "room": adress.value + buliding.value + domiNumber.value,
                "appointment": parseInt(times.value),
                "appointDate": new Date().getFullYear()+"-"+month.value + '-' + days.value,
            }),
            success: function (msg) {
                if (msg.code == 201) {
                    alert("报修单提交成功");
                    alert(new Date().getFullYear());
                    location.reload();
                }
            },
            error: function (xhr) {
                alert(xhr.status);
            }
        })
    }
})
