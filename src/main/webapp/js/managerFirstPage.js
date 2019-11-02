// function bodyScale() {
//     var devicewidth = document.documentElement.clientWidth;
//     var scale = devicewidth / 1440;
//     document.body.style.zoom = scale;
// }
// window.onload = window.onresize = function () {
//     bodyScale();
// };
var transform=document.getElementsByClassName("category");
transform[0].onclick=function(){
    window.location.href="../notice.html";
}
transform[1].onclick=function(){
    window.location.href="../repair.html";
}
transform[2].onclick=function(){
    window.location.href="/communication.html";
}