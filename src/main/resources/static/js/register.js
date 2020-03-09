/*
* 注册页面js
* */

/*
* 对用户名进行校验
* */
function repetitionName(e) {
    var name = $("#name")
    if (e == '') {
        name.text("用户名不能为空")
        return;
    }
    $.ajax({
        type: "post",
        url: "/registerNameVerify",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "name": e,
        }),
        success: function (response) {//成功回调函数
            if (response.code == 200) {
                name.hide()
                return;
            }
            name.show();
            name.text(response.message)

        }
    });
}

/*
* 对密码进行校验
* */
function repetitionPassword(e) {
    var password = $("#password")
    if (e == '') {
        password.text("密码不能为空")
        return;
    }
    $.ajax({
        type: "post",
        url: "/registerPwdVerify",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "password": e,
        }),
        success: function (response) {//成功回调函数
            if (response.code == 200) {
                password.hide()
                return;
            }
            password.show();
            password.text(response.message)

        }
    });
}

/*
* 判断两次密码是否一致
* */
function registerPwdRepetitionVerify() {
    var passwordFirst = $("#passwordFirst").val()
    var passwordRepetition = $("#passwordRepetition").val()
    var RepetitionPassword = $("#RepetitionPassword")
    if (passwordFirst == passwordRepetition) {
        RepetitionPassword.hide()
        return;
    } else {
        RepetitionPassword.show();
        RepetitionPassword.text("密码不一致")
        return;
    }
}

/*
* 用户选择默认头像
* */
function headPortrait(obj) {
    $(obj).addClass("style_img")
    var oneself = $(obj)[0].src
    $("img").each(function () {
        var rest = $(this)[0].src
        if (oneself == rest) {
        } else {
            $(this).removeClass("style_img")
        }
    })

}

/*
* 用户点击注册后，提交页面信息到数据库
* */
function registerR() {
    registerPwdRepetitionVerify();
    var passwordFirst = $("#passwordFirst").val();
    var nameInput = $("#nameInput").val();
    var register = $("#register");
    register.hide()
    var hade="";
    $("img").each(function () {
      var img=$(this).hasClass("style_img");
      if (img){
          hade=$(this)[0].src;
      }
    })
    $.ajax({
        type: "post",
        url: "/registerInsert",
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify({
            "password": passwordFirst,
            "name":nameInput,
            "head":hade
        }),
        success: function (response) {//成功回调函数
            if (response.code == 200) {
                window.location.href="/"
                return;
            }
            if (response.code==2018){
                repetitionName(nameInput);
                return;
            }
            if (response.code=2020){
                repetitionPassword(passwordFirst)
            }
        }
    });
}



