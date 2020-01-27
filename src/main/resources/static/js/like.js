/*点击相关问题后跳转页面*/
function  like(e) {
    var attribute = e.getAttribute("data-id");
    window.location.href="/question/"+attribute;
}