function  dianji(e) {
    var attribute = e.getAttribute("data-id");
    /*首先将通知数表为以阅读*/
    window.location.reload();
    window.location.href="/NotController/"+attribute;

}