function addbiaoqian(e) {
    var attribute = e.getAttribute("data-id");
    var val = $("#tag").val();
    var c=val.split(",");
    /*判断添加的内容是否重复*/
    for (var i=0;i<c.length;i++){
        if (c[i]==attribute){
            alert("标签重复添加")
            return;
        }
    }
    if (val.length==0){
        $("#tag").val(val+attribute)
    }else {
        $("#tag").val(val+","+attribute)
    }
}