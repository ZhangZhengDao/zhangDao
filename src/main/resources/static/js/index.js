function github() {
    window.location.href = "/github"

}

function Denglu() {
    var name = $("#inputEmail3").val();
    var password = $("#inputPassword3").val();
    $.ajax({
        type: "post",
        url: "/Denglu",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "name": name,
            "password": password,
            "type": 1
        }),
        success: function (response) {//成功回调函数
            if (response.code == 2014) {
                alert("登陆失败")
            }
            if (response.code == 2000) {
                window.location.reload();
            }
        }
    })
}

/*添加好友发送验证信息*/
function yanzheng(e) {
    var userid = e.getAttribute("data-id");
    $.ajax({
        type: "post",
        url: "/addFriend",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "userid": userid,
        }),
        success: function (response) {//成功回调函数
            if (response.code == 2003) {
                alert("您还没有登录请先登录")
            }
            if (response.code == 200) {
                window.location.reload();
            }
        }
    })
}

/*取消关注*/
function quxiao(e) {
    var userid = e.getAttribute("data-id");
    $.ajax({
        type: "post",
        url: "/reomFriend",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "userid": userid,
        }),
        success: function (response) {//成功回调函数
            if (response.code == 2003) {
                alert("您还没有登录请先登录")
            }
            if (response.code == 200) {
                window.location.reload();
            }
        }
    })
}

//全局变量
var attribute = "";

/*点击会话窗口*/
function huihua(e) {
    //会话对象的信息
    var account = e.getAttribute("data-id");
    var xinxix = account.split(",");
    attribute = xinxix[0];
    $("#huihua").text("与用户" + xinxix[1] + "对话");
    var div = $('#xian');
    //给服务端的发送 表示 当前用户打开了消息窗口
    $.ajax({
        type: "post",
        url: "/KUstat",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "static": xinxix[0],//发送给服务前 当前消息窗口的状态 被聊天的用户
        }),
        //拿到消息记录
        success: function (response) {//成功回调函数
            var aa=JSON.parse(response);
            var list = aa.list;
            var user = aa.user;
            var daccount=aa.daccount;
            for (var listKey in list) {
                var splic=list[listKey].split(":");
                if (splic[0]!=attribute){
                    //证明是当前用户的消息 在右侧显示
                    var c_a = $("<img/>", {
                        "class": "img-circle",
                        "src": aa.account,
                        style: 'width: 55px;height: 55px'
                    });
                    var c = $("<div/>", {
                        "class": "media-left",
                        style: "float:right;"
                    });

                    var c1_a3 = $("<p/>", {
                        "class": "label label-success",
                        html: splic[2],
                        style: "font-size:15px;white-space: pre-wrap !important;"
                    })
                    var c1_a2 = $("<div/>", {
                        "class": "mun",
                        style: "color: #999;margin-top: 30px;float: right;margin-right: 5px;",

                    });
                    var c1 = $("<div/>", {
                        "class": "media-body",
                    });
                    var b = $("<div/>", {
                        "class": "media",
                        style: "    margin-top: 10px;"
                    });
                    var a = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                        id:"1212"
                    });

                    c1_a2.append(c1_a3)
                    c1.append(c1_a2);
                    c.append(c_a);
                    b.append(c);
                    b.append(c1);
                    a.append(b);
                    div.before(a);//在被选元素之后插入内容
                }else{
                    //对方消息则在左侧显示
                    var c_a=$("<img/>",{
                        "class": "img-circle",
                        "src":daccount.avatarUrl,
                        style: 'width: 55px;height: 55px'
                    });
                    var c=$("<div/>",{
                        "class": "media-left",
                        style:"float:left;margin-left: 10px;"
                    });
                    var c1_a3=$("<p/>",{
                        "class":"label label-success",
                        html:splic[2],
                        style:"font-size:15px;white-space: pre-wrap !important;"
                    })
                    var c1_a2=$("<div/>",{
                        "class": "mun",
                        style:"color: #999;margin-top: 20px;",
                    });
                    var c1=$("<div/>",{
                        "class": "media-body",
                    });
                    var b=$("<div/>",{
                        "class": "media",
                        style:"    margin-top: 10px;"
                    });
                    var a = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
                    });
                    c1_a2.append(c1_a3)
                    c1.append(c1_a2);
                    c.append(c_a);
                    b.append(c);
                    b.append(c1);
                    a.append(b);
                    div.before(a);
                }
            }
        }
    });

    $('#gunlun')[0].scrollTop = $('#gunlun')[0].scrollHeight;
}

/*关闭消息窗口调用此方法*/
function guanbi() {
    $.ajax({
        type: "post",
        url: "/KUstat",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "static": 0//发送给服务前 当前消息窗口的状态，记录他打开了与谁的 聊天窗口
        }),
        success: function (response) {//成功回调函数
        window.location.reload();
        }
    });
}

/*向服务端发送消息*/
function sendc(e) {
    var useraccount = e.getAttribute("data-id").split(",");
    /*将消息封装*/
    var daccount = attribute;
    var text = $("#xinxinei").val();
    var accout = useraccount[0];
    var url = useraccount[1];//发送者 头像
    var div = $('#xian');//需要向这个div添加消息内容
    $.ajax({
        type: "post",
        url: "/jieshou",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "account": accout,//发送人account
            "text": text,//发送内容
            "daccount": daccount//接收人account
        }),
        success: function (response) {//成功回调函数

            if (response.code==2003){
                alert(response.message);
                return ;
            }
            if (response.code==2015){
                alert(response.message);
                return;
            }

            if (response==-1){
                alert("对方没有关注你，或者取消了对你的关注，你的消息会以通知的形式发送给他");
            }
            //把个人信息写入页面 ，个人信息 显示在最有边
            var c_a = $("<img/>", {
                "class": "img-circle",
                "src": url,
                style: 'width: 55px;height: 55px'
            });
            var c = $("<div/>", {
                "class": "media-left",
                style: "float:right;"
            });

            var c1_a3 = $("<p/>", {
                "class": "label label-success",
                html: text,
                style: "font-size:15px;white-space: pre-wrap !important;"
            })
            var c1_a2 = $("<div/>", {
                "class": "mun",
                style: "color: #999;margin-top: 30px;float: right;margin-right: 5px;",

            });
            var c1 = $("<div/>", {
                "class": "media-body",
            });
            var b = $("<div/>", {
                "class": "media",
                style: "    margin-top: 10px;"
            });
            var a = $("<div/>", {
                "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12",
            });

            c1_a2.append(c1_a3)
            c1.append(c1_a2);
            c.append(c_a);
            b.append(c);
            b.append(c1);
            a.append(b);
            div.before(a);//在被选元素之后插入内容
            $('#gunlun')[0].scrollTop = $('#gunlun')[0].scrollHeight;
        }
    });
}
