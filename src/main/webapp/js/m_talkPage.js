$(document).ready(function () {

    $('#talk').click(function () {
        window.location.href = "communication.html";
    });

    $("#back").click(
        function () {
            window.location.href ="index.do";
        }
    );


});


function getBasePath() {
    let location = (window.location+'').split('/');
    return location[0] + '//' + location[2] + '/';
}

function getBasePath2() {
    let location = (window.location+'').split('/');
    return  location[2] + '/';
}

