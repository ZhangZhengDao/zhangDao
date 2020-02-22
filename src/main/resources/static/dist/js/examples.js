$(function () {

    /**
     * Some examples of how to use features.
     *
     **/
    var daccount = "";//被点击用户daccount

    var ChatosExamle = {
        Message: {
            add: function (message, type) {
                var chat_body = $('.layout .content .chat .chat-body');
                /*添加元素*/
                var name = $("#SessionName").val();
                var url = $("#SessionUrl").val();
                zuo(message, 1, url, name, "append",shijan());
                chat_body.scrollTop(chat_body.get(0).scrollHeight, -1).niceScroll({
                    cursorcolor: 'rgba(66, 66, 66, 0.20)',
                    cursorwidth: "4px",
                    cursorborder: '0px'
                });
            }
        }
    };

    setTimeout(function () {
        // ChatosExamle.Message.add();
    }, 1000);

    setTimeout(function () {
        // $('#disconnected').modal('show');
        // $('#call').modal('show');
    }, 2000);

    $(document).on('submit', '.layout .content .chat .chat-footer form', function (e) {
        e.preventDefault();
        var input = $(this).find('input[type=text]');
        var message = input.val();
        message = $.trim(message);
        if (message == "") {
            return;
        }
        $.ajax({
            type: "post",
            url: "/jieshou",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "text": message,//发送内容
                "dAccount": daccount,//接收人account
                "data":shijan()
            }),
            success: function (response) {//成功回调函数
                if (response.code == "2015") {
                    alert("你并不是对方好友");
                    input.focus();
                } else {
                    ChatosExamle.Message.add(message, 'outgoing-message');
                    input.val('');
                }
                chat_body()
            }
        })
    });

    /*点击好友列表后执行*/
    $(document).on('click', '.layout .content .sidebar-group .sidebar .list-group-item ', function () {
        $("#liaotian").empty();//加入之前 清除样式
        if (jQuery.browser.mobile) {
            $(this).closest('.sidebar-group').removeClass('mobile-open');
        }
        /*增加点击后的样式 增加之前先清除样式*/
        $(".list-group-item").removeClass("open-chat")
        $(this).closest(".list-group-item").addClass("open-chat")
        var message = $(this).find('input[type=hidden]');
        daccount = message.val();
        removeCla(daccount);
        //向前端查询数据
        $.ajax({
            type: "post",
            url: "/KUstat",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "static": message.val(),//发送给服务前 当前消息窗口的状态 被聊天的用户
            }),
            //拿到消息记录
            success: function (response) {//成功回调函数
                if (JSON.parse(response).code == 2003) {
                    alert("未登录，请登录");
                }
                var aa = JSON.parse(response);
                var daccount = aa.daccount;
                var list = aa.list;
                var user = aa.user;
                /*刷新头像用户名等信息*/
                $("#Username").text(daccount.name);
                $("#UserUrl").attr("src", daccount.avatarUrl);
                /*固定在底部*/
                var div = $("#liaotian");
                var A2 = $("<div/>", {
                    style: "width:100%",
                    id: "diandi"
                });
                var url = $("#SessionUrl").val();
                var name = $("#SessionName").val();
                div.prepend(A2)
                for (var listKey in list.reverse()) {
                    var splic = list[listKey].split(":");
                    if (splic[0] != message.val()) {
                        if (splic[3]!=''){
                            zuo(splic[2], 1, url, name, "",splic[3]);
                        }else{
                            zuo(splic[2], 1, url, name, "",);
                        }
                    } else {
                        if (splic[3]!=''){
                            zuo(splic[2], 2, daccount.avatarUrl, daccount.name, "",splic[3]);
                        }else {
                            zuo(splic[2], 2, daccount.avatarUrl, daccount.name, "");
                        }
                    }
                }
                chat_body();
            }
        });
    });

    //获取服务器要向客户端发送的内容
    webSocket.onmessage = function (event) {
        var user = JSON.parse(event.data).message;
        if (user.stat == 4) {
            var data = user.text;//消息内容
            var name = user.userName;//发送者姓名
            var url = user.userUrl;
            var data1 = user.data;
            data1=timeTranslation(data1);
            var div = $("#liaotian");
            var a_a_a_a1_a1_a = $("<i/>", {
                "class": "ti-double-check text-info",
            });
            var a_a_a_a1_a1 = $("<div/>", {
                "class": "time",
                html: data1
            });
            var a_a_a_a1_a = $("<h5/>", {
                text: name
            });
            var a_a_a_a1 = $("<div/>", {});
            var a_a_a_a_a = $("<img/>", {
                class: "rounded-circle",
                "src": url,
                "alt": "image"
            });
            var a_a_a_a = $("<figure/>", {
                "class": "avatar",
            });
            var a_a_a = $("<div/>", {
                "class": "message-avatar",
            });
            var a_a = $("<div/>", {
                "class": "message-item ",
            });
            var a_a1 = $("<div/>", {
                "class": "message-content",
                html: data
            });
            var A = $("<div/>", {
                "class": "messages"
            });
            a_a_a_a.append(a_a_a_a_a)
            a_a_a.append(a_a_a_a)
            //a_a_a_a1_a1.append(a_a_a_a1_a)//h5
            a_a_a_a1_a1.append(a_a_a_a1_a1_a)
            a_a_a_a1.append(a_a_a_a1_a)
            a_a_a_a1.append(a_a_a_a1_a1)
            a_a_a.append(a_a_a_a1)
            a_a.append(a_a_a)
            a_a.append(a_a1)
            A.append(a_a);
            div.append(A)
            chat_body();
        } else {
            //提示用户，有消息来了，查收
            // confirm('你收到了来自用户：' + JSON.parse(event.data).account.name + '的信息是否查看')
            $("#p" + user.userAccount).text(user.text);
            andCla(user.userAccount);
        }
    }

    /*向页面添加数据方法*/
    function zuo(message, type, url, name, tianjia,data) {
        data=timeTranslation(data);
        var div = $("#liaotian");
        if (type == 1) {
            var a_a_a_a1_a1_a = $("<i/>", {
                "class": "ti-double-check text-info",
            });
            var a_a_a_a1_a1 = $("<div/>", {
                "class": "time",
                html: data
            });
            var a_a_a_a1_a = $("<h5/>", {
                text: name
            });
            var a_a_a_a1 = $("<div/>", {});
            var a_a_a_a_a = $("<img/>", {
                class: "rounded-circle",
                "src": url,
                "alt": "image"
            });
            var a_a_a_a = $("<figure/>", {
                "class": "avatar",
            });
            var a_a_a = $("<div/>", {
                "class": "message-avatar",
            });
            var a_a = $("<div/>", {
                "class": "message-item outgoing-message",
            });
            var a_a1 = $("<div/>", {
                "class": "message-content",
                html: message
            });
            var A = $("<div/>", {
                "class": "messages"
            });
            a_a_a_a.append(a_a_a_a_a)
            a_a_a.append(a_a_a_a)
            //a_a_a_a1_a1.append(a_a_a_a1_a)//h5
            a_a_a_a1_a1.append(a_a_a_a1_a1_a)
            a_a_a_a1.append(a_a_a_a1_a)
            a_a_a_a1.append(a_a_a_a1_a1)
            a_a_a.append(a_a_a_a1)
            a_a.append(a_a_a)
            a_a.append(a_a1)
            A.append(a_a);
            if (tianjia == "append") {
                div.append(A)
            } else {
                div.prepend(A);
            }
        } else {
            var a_a_a_a1_a1_a = $("<i/>", {
                "class": "ti-double-check text-info",
            });
            var a_a_a_a1_a1 = $("<div/>", {
                "class": "time",
                html: data
            });
            var a_a_a_a1_a = $("<h5/>", {
                text: name
            });
            var a_a_a_a1 = $("<div/>", {});
            var a_a_a_a_a = $("<img/>", {
                class: "rounded-circle",
                "src": url,
                "alt": "image"
            });
            var a_a_a_a = $("<figure/>", {
                "class": "avatar",
            });
            var a_a_a = $("<div/>", {
                "class": "message-avatar",
            });
            var a_a = $("<div/>", {
                "class": "message-item ",
            });
            var a_a1 = $("<div/>", {
                "class": "message-content",
                html: message
            });
            var A = $("<div/>", {
                "class": "messages"
            });
            a_a_a_a.append(a_a_a_a_a)
            a_a_a.append(a_a_a_a)
            //a_a_a_a1_a1.append(a_a_a_a1_a)//h5
            a_a_a_a1_a1.append(a_a_a_a1_a1_a)
            a_a_a_a1.append(a_a_a_a1_a)
            a_a_a_a1.append(a_a_a_a1_a1)
            a_a_a.append(a_a_a_a1)
            a_a.append(a_a_a)
            a_a.append(a_a1)
            A.append(a_a);
            div.prepend(A)
        }
        chat_body()
    }

    /*滚轮移动到最下面*/
    function chat_body() {
        var chat_body = $('.layout .content .chat .chat-body');
        chat_body.scrollTop(chat_body.get(0).scrollHeight, -1).niceScroll({
            cursorcolor: 'rgba(66, 66, 66, 0.20)',
            cursorwidth: "4px",
            cursorborder: '0px'
        });
    }

    /*向服务器发送消息*/
    function senUserData(text, daccount) {
        if (test == "") {
            alert("不能发送空消息")
        }
        $.ajax({
            type: "post",
            url: "/jieshou",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "text": text,//发送内容
                "dAccount": daccount//接收人account
            }),
            success: function (response) {//成功回调函数
                if (response.code == "2015") {
                    alert("你并不是对方好友");
                    return "false";
                } else {
                    boolean = "true";
                }
            }
        })
        alert(boolean)
    }

    /*收到消息后的样式*/
    function andCla(userdaccount) {
        //移除时间
        $("#time" + userdaccount).hide();
        var div = $("#zuixin" + userdaccount);
        var circle = $("#circle" + userdaccount).text();
        if (circle == "") {
            circle = 0
            var a_a2 = $("<small/>", {
                "class": "text-primary",
                html: "有新的消息"
            });
            var a_a1 = $("<div/>", {
                "class": "new-message-count",
                id: "circle" + userdaccount,
                html: 1
            });
            var a = $("<div/>", {
                "class": "users-list-action",
                id: "circularize" + userdaccount
            });
            a.append(a_a1);
            a.append(a_a2);
            div.after(a);
            $("#zuixin-h5" + userdaccount).addClass("text-primary");
        } else {
            $("#circle" + userdaccount).text((parseInt(circle) + parseInt(1)));
        }
    }

    /*去除用户点击后的样式*/
    function removeCla(userdaccount) {
        $("#circularize" + userdaccount).remove();
        ;
        $("#zuixin-h5" + userdaccount).removeClass("text-primary");
        $("#time" + userdaccount).show();
    }


    /*
    * 根据用户查询，跟人信息
    * */
    $(document).on('click', '.dropdown-item', function () {
        var message = $(this).find('input[type=hidden]');
        $("#friendBiaoqian").empty()
        //根据account查询出用户的信息
        var account=message.val();
        if (account==''){
            account=$("#account").val();
        }
        $.ajax({
            type: "post",
            url: "/getUserIntroduce",
            contentType: "application/json;charset=UTF-8",
            data: JSON.stringify({
                "account": account,
            }),
            success: function (response) {//成功回调函数
                var data = JSON.parse(response);
                $("#friendName").text(data.peopleDTO.user.name)
                $("#friendUrl").attr('src', data.peopleDTO.user.avatarUrl)
                $("#friendIntro").text("当前用户未填写详细信息")
                $("#friendDianzan").text("点赞数" + data.peopleDTO.dinazan)
                $("#friendPinglunhuifu").text("评论回复数" + data.peopleDTO.pinglunhuifu)
                $("#friendQhuifu").text("问题回复数" + data.peopleDTO.qhuifu)

                $("#friendWentishu").text("发布问题" + data.peopleDTO.wentishu)
                $("#friendYuedu").text("累计阅读量" + data.peopleDTO.yuedu)
                if (data.peopleDTO.stat == 1) {
                    $("#friendStat").text("在线")
                } else {
                    $("#friendStat").text("未在线")
                }
                if (data.UserTag.leng != 0) {
                    for (var dataKey in data.UserTag) {
                        Addbioaqian(dataKey, data.UserTag[dataKey])
                    }
                }
            }
        });
    });
})

function Addbioaqian(datakey, text) {
    var div = $("#friendBiaoqian");
    var a = $("<p/>", {
        html: text
    });
    a.append(a)
    div.append(a);
}

/*系统当前时间*/
function shijan() {
    LocalTime()
    var myDate = new Date();
//获取当前年
    var year = myDate.getFullYear();
//获取当前月
    var month = myDate.getMonth() + 1;
//获取当前日
    var date = myDate.getDate();
    var h = myDate.getHours();       //获取当前小时数(0-23)
    var m = myDate.getMinutes();     //获取当前分钟数(0-59)
    var s = myDate.getSeconds();
    var now = year + '-' + LocalTime(month) + "-" + LocalTime(date) + " " + LocalTime(h) + ':' + LocalTime(m) + ":" + LocalTime(s);

    return now;
}


function LocalTime(s) {
    return s < 10 ? '0' + s : s;
}
//通知用户关注
function friendNoit(e) {
    var id = e.getAttribute("data-id");
    $.ajax({
        type: "post",
        url: "/friendNoit",
        contentType: "application/json;charset=UTF-8",
        data:JSON.stringify({
            "Noitid":id,
        }),
        success: function (response) {//成功回调函数
            alert("通知成功，请留意好友列表哦")
        }
    });
}
/*将时间转译*/
function timeTranslation(Time) {
     return Time;
}

