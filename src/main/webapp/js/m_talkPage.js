$(document).ready(function () {

    $('#talk').click(function () {
        window.location.href = "communication.html";
    });
});


function getBasePath() {
    let location = (window.location+'').split('/');
    return location[0] + '//' + location[2] + '/';
}

function getBasePath2() {
    let location = (window.location+'').split('/');
    return  location[2] + '/';
}
