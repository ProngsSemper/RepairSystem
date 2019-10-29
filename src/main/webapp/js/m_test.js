$(document).ready(function () {
    var p ={
        "code": 200,
        "data": 3,
        "desc": "查询成功",
        "paths": {
            "你爸爸": "F:/算法/我的团队项目/p1/target/RepairSystem/upload/excel/2019-10-29/2019-10-29你爸爸.xls",
            "所有人": "F:/算法/我的团队项目/p1/target/RepairSystem/upload/excel/2019-10-29/2019-10-29_all_.xls",
            "压缩包": "F:/算法/我的团队项目/p1/target/RepairSystem/upload/zip/2019-10-29.zip"
        }
    };


    // for(let key in p.paths){
    //     var location = (window.location+'').split('/');
    //     var basePath = location[0]+'//'+location[2]+'/'+location[3];
    //
    //     var string = p.paths[key].substring(0,p.paths[key].indexOf("upload"));
    //     // alert(p.paths[key].replace(string,"localhost/"));
    //     let url = p.paths[key].replace(string,basePath);
    //     alert(url);
    //
    //     $(".excelBox").append('<a href="'+url+'" download="'+url+'">'+"文件："+key+'</a>');
    //
    // }

});