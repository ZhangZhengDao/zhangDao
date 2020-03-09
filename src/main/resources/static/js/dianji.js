function  dianji(e) {
    var attribute = e.getAttribute("data-id");
    /*首先将通知数表为以阅读*/
    var c=attribute.split(",");
    window.location.reload();
    if (c[1]=="5") {
        window.location.href = "/NotControllerfriend/" +c[0];
    }
    if (c[1]!="5") {
        window.location.href = "/NotController/" +c[0];
    }
}
/*收到关注申请后关注申请用户*/
function friendUser(e) {
    var id = e.getAttribute("data-id");
    $.ajax({
        type: "post",
        url: "/NoitFriend",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "Noitid":id,
        }),
        success: function (response) {//成功回调函数
            alert("关注成功")
            window.location.reload()
        }
    });
}