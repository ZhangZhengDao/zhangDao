function adder(e) {
    var id = e.getAttribute("data-id");
    var content = $("#erere"+id).val();
    $.ajax({
        type: "post",
        url: "/Commenter",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "id":id,
            "content":content
        }),
        success: function (response) {//成功回调函数
            if (response.code==200){
                window.location.reload()
            }
            if (response.code==2003) {
                var v = confirm(response.message);
                if (v) {
                    window.open("/github");
                    window.localStorage.setItem("closable", true);
                }
            }
        }
    })
}