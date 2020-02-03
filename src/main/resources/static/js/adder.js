//二级回复添加：动态添加，先向页面添加元素
function adder(e) {
    var id = e.getAttribute("data-id");
    var content = $("#erere"+id).val();
    var quan = $("#erere1"+id).text();
    if (quan!="@"){
        content=quan+content;
    }
    //使被回复的评论加一
    var yi=$("#pinglun-yi"+id).text();
    yi=parseInt(yi)+parseInt(1);
    $("#pinglun-yi"+id).text(yi);
    //拿到二级回复div id
    var div=$("#comment-"+id);
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
                //不使用页面刷新选择直接向上加样式内容
                // window.location.reload()
                comment=response.list;
                var c_a=$("<img/>",{
                    "class": "img-rounded",
                    "src":comment[0].user.avatarUrl,
                    style: 'width: 55px;height: 55px'
                });
                var c=$("<div/>",{
                    "class": "media-left",
                });
                var c1_a1=$("<h5/>",{
                    "class": "media-heading",
                    html:comment[0].content
                });
                var c1_a=$("<h4/>",{
                    "class": "media-heading",
                    html:comment[0].user.name,
                    "id":"usernameid"+comment[0].id
                });
                /*评论，回复，时间*/
                var c1_a2_a_a=$("<span/>",{
                    "class": "glyphicon glyphicon-thumbs-up iun",
                    style:"margin-right: 10px;"
                });
                var c1_a2_a_a1=$("<span/>",{
                    "class": "glyphicon glyphicon-comment iun",
                    "onclick":"erji("+"'"+id+","+comment[0].id+"'"+")"
                });
                var c1_a2_a=$("<h4/>",{
                    "class": "mun",
                });
                var c1_a2=$("<div/>",{
                    "class": "mun",
                    style:"color: #999;margin-top: 10px;"
                });
                var c1=$("<div/>",{
                    "class": "media-body",
                });
                var b=$("<div/>",{
                    "class": "media",
                });
                var a = $("<div/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                });
                var b2 = $("<hr/>", {
                    "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                    style:"margin-top:0px;"
                });
                c1_a2_a.append(c1_a2_a_a);
                c1_a2_a.append(c1_a2_a_a1);

                c1_a2.append(c1_a2_a)

                c1.append(c1_a);
                c1.append(c1_a1);
                c1.append(c1_a2);
                c.append(c_a);
                b.append(c);
                b.append(c1);
                a.append(b);
                a.append(b2)
                div.prepend(a);
                //清除输入框内容
                $("#erere"+id).val("");
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