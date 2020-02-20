function fawen() {
    //显示发文，隐藏回复
    $("#hu").removeClass("active");
    $("#fa").addClass("active");
    $("#huifu").hide();
    $("#fawen").show();
}
function huifu() {
    //显示发文，隐藏回复
    $("#hu").addClass("active");
    $("#fa").removeClass("active");
    $("#huifu").show();
    $("#fawen").hide();
}
/*点击后跳转页面*/
function question(e) {
    var attribute = e.getAttribute("data-id");
    window.location.href='/question/'+attribute
}