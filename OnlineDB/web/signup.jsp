<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%

    Cookie cookie = null;
    Cookie[] cookies = null;
    // 获取当前域名下的cookies，是一个数组
    cookies = request.getCookies();
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {//删除cookie中记住的用户名
            cookie = cookies[i];
            if (((cookie.getName()).compareTo("username") == 0) || (cookie.getName()).compareTo("user_nameId") == 0) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>在线数据库管理系统</title>
    <link href="style/authority/login_css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <script language="javascript">
        function checkTel() {
            var tel = document.getElementById("phone").value;
            var telLen = tel.length;
            var telInfo = document.getElementById("telInfo");
            if (telLen != 11) {
                telInfo.innerHTML = '必须11位数';
            } else if (telLen == 11) {
                telInfo.innerHTML = '';
            }
        }
        function pd(obj) {
            var psw1 = document.getElementById("password").value;
            var psw2 = document.getElementById("password2").value;
            var pswInfo = document.getElementById("pswInfo");
            if (psw1 != psw2) {
                pswInfo.innerHTML = '密码不同';
                obj.value = "";
            }
        }
    </script>
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form id="submitForm" action="signup" method="POST">
                    <div>
                        用&nbsp;&nbsp;户&nbsp;&nbsp;名：<input type="text" name="username" class="username" id="name"
                                                           required><font color="red">*</font>
                    </div>
                    <div>
                        邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：<input type="email" name="email" class="email"
                                                                            id="email" required><font
                            color="red">*</font>
                    </div>
                    <div>
                        手&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机：<input type="tel" name="userEntity.tel" class="tel"
                                                                            id="phone" onkeyup="checkTel()"><span
                            id="telInfo"></span>
                    </div>
                    <div>
                        密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" class="pwd"
                                                                            id="password" required><font
                            color="red">*</font>
                    </div>
                    <div>
                        重输密码：<input type="password" name="password2" class="pwd" id="password2" required
                                    onblur="pd(this);"><font color="red">*</font> <span id="pswInfo"></span>
                    </div>

                    <div id="btn_area">
                        <input type="submit" class="signup_btn" id="signup_sub" value="注 册">
                        <input type="reset" class="signup_btn" id="signup_ret" value="重 置">
                    </div>
                    <div class="a_link">
                        <a href="login.jsp">已有账号，点击登录</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div style="display:none">
    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>
</div>
</body>
</html>