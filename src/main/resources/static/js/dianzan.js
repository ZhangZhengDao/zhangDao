/*点赞添加*/
function  dianzan(e) {
    var attribute = e.getAttribute("data-id");
    var a=$("#eeec"+attribute).text();
    var t=a;
    $.ajax({
        type: "post",
        url: "/adddianzan",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "id":attribute
        }),
        success: function (response) {//成功回调函数
            var c=response.id;
            //根据拿到的id去更改点赞按钮的颜色
            if (c==1){
                //点赞状态
               var aa= $("#eeec"+attribute)
                /*aa.text(aa.val+1)*/
                t=parseInt(t)+1
                $("#eeec"+attribute).text(t)
                aa.addClass("true")
            }else{
                //未点赞状态
                var aa= $("#eeec"+attribute)
                t=parseInt(t)-1
                $("#eeec"+attribute).text(t)
                aa.removeClass("true")
                aa.removeClass("dian")
            }
            if (response.code==200){
                window.location.reload()
            }
            /*用户未登录提示登录*/
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