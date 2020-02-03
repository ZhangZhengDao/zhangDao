/*二级评论*/
function get(e) {
    //拿到用户点击的id
    var id = e.getAttribute("data-id");
    var comments = $("#a"+id);
    //获取二级评论展开状态
    var attribute1 = e.getAttribute("data-collapse");
    if (attribute1) {
        //再次点击则表示关闭
      comments.hide();
        //并将状态变为空
        e.removeAttribute("data-collapse");
        return;
    }
    //向数据库查询信息--再向页面添加数据
    $.getJSON({
        url: "/getCommenter/"+id,
        contentType: "application/json;charset=UTF-8",
        type:'post',
        success: function (response) {//成功回调函数
           var comment=response.list;
           var commm=$("#comment-"+id);
           commm.empty();
            for(var i in comment.reverse()){//.reverse()颠倒数组里面的元素
                var c_a=$("<img/>",{
                    "class": "img-rounded",
                    "src":comment[i].user.avatarUrl,
                    style: 'width: 55px;height: 55px'
                });
                var c=$("<div/>",{
                    "class": "media-left",
                });
                var c1_a1=$("<h5/>",{
                    "class": "media-heading",
                    html:comment[i].comment.content
                });
                var c1_a=$("<h4/>",{
                    "class": "media-heading",
                    html:comment[i].user.name,
                    "id":"usernameid"+comment[i].comment.id+""
                });
                /*评论，回复，时间*/
                var c1_a2_a_a=$("<span/>",{
                    "class": "glyphicon glyphicon-thumbs-up iun",
                    style:"margin-right: 10px;"
                });
                var c1_a2_a_a1=$("<span/>",{
                    "class": " iun",
                    "onclick":"erji("+"'"+id+","+comment[i].comment.id+"'"+")",
                    text:"回复",
                    style:"font-size:15px;margin-left: -5px;float: right;"
                });
             /*   var c1_a2_a_a=$("<span/>",{
                    "class": "mun",
                });*/

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
                commm.prepend(a);
            }
        }
    });
    //展开二级评论后标记
    comments.show();
    e.setAttribute("data-collapse", "in");
}