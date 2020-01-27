function typenone(e) {
    var attribute = e.getAttribute("data-id");
    var c=["开发语言","平台框架","服务器","数据库","开发工具"];
    var a = $("#li"+attribute);
    a.addClass("active")
    for (var i=0;i<c.length;i++){
        if ("li"+attribute=="li"+c[i]){
            continue;
        }
        $("#li"+c[i]).removeClass("active")
    }
    for (var i=0;i<c.length;i++){
        $("#"+c[i]).hide();
    }
    for (var i=0;i<c.length;i++){
        if (attribute==c[i]){
            $("#"+attribute).show();
            continue;
        }
        $("#"+c[i]).hide();
    }
}