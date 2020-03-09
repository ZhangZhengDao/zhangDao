
function post(){
    var id=$("#id").val();
    var text= $("#comment_text").val();
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "parentId":id,
            "content":text,
            "type":1
        }),
        success: function (response) {//成功回调函数
            /*alert(1)
            alert(response.code);*/
            if(response.code==200){
                $("#comm").hide();
                //提交成功后直接让页面刷新
                window.location.reload();
            }else {
                if (response.code==2003){
                    var  v=confirm(response.message);
                    if (v){
                        window.open("https://github.com/login/oauth/authorize?client_id=43eadde0b6dd72f33590&redirect_url=http://localhost:8887//callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
        }
    });
}
