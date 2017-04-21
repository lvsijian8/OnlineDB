<%@ page language="java" pageEncoding="UTF-8" %>

<%
    Cookie cookie = null;
    Cookie[] cookies = null;
    // 获取cookies的数据,是一个数组
    cookies = request.getCookies();
    boolean mark = false;//标记是否登陆
    String username = "username";
    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().toString().equals(username)) {//判断是否有保存用户
                mark = true;
                break;
            }
        }
    }
    if (mark) {//有保存
%>
<jsp:forward page="indexLoad"/>
<%
} else if (!mark) {//无保存
%>
<jsp:forward page="login.jsp"/>
<%
    }
%>