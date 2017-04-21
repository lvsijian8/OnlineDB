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
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form id="submitForm" action="Login" method="POST">
                    <div id="login_tip">
                        <span id="login_err" class="sty_txt2"></span>
                    </div>
                    <div>
                        用户名：<input type="text" name="username" class="username" id="name" required>
                    </div>
                    <div>
                        密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" class="pwd" id="pwd"
                                                          onkeypress="EnterPress(event)" onkeydown="EnterPress()"
                                                          required>
                    </div>
                    <div id="btn_area">
                        <input type="submit" class="login_btn" id="login_sub" value="登  录">
                        &nbsp;&nbsp;&nbsp;<input type="checkbox" name="autologin"/>&nbsp;自动登录
                    </div>
                    <div class="a_link">
                        <a href="signup.jsp">若无账号，点击注册</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>