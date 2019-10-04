function bodyScale() {
    let devicewidth = document.documentElement.clientWidth;
    let scale = devicewidth / 1440;
    document.body.style.zoom = scale;
}
window.onload = window.onresize = function () {
    bodyScale();
};