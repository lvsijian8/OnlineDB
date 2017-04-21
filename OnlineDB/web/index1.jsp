<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>在线数据库管理系统</title>
    <link href="style/authority/main_css.css" rel="stylesheet" type="text/css"/>
    <script src="scripts\jquery\jquery-1.4.4.min.js"></script>
    <script src="jquery.treemenu.js"></script>

    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script language="javascript">
        function ajaxS(ceng, x, y, z, obj) {//左侧数据库连接列表点击事件
            $.ajax({
                method: "post",
                url: 'indexLoad',
                data: {'ceng': ceng, 'x': x, 'y': y, 'z': z},//通过POST模式与indexLoad交互
                success: function (data) {//交互成功后将返回信息输出至HTML相应位置
                    switch (ceng) {
                        case 0: {//点击连接
                            alert( "Data Saved: " + data );
                            var id = "0" + x + "-1-1";
                            $('#' + id)[0].innerHTML = data;
                            $(obj).next(".slist").animate({height: 'toggle', opacity: 'toggle'}, "slow");
                            break;
                        }
                        case 1: {//点击数据库
                            var id = "1" + x + y + "-1";
                            $('#' + id)[0].innerHTML = data;
                            $(obj).next(".sslist").animate({height: 'toggle', opacity: 'toggle'}, "slow");
                            break;
                        }
                        case 2: {//点击表
                            var id = "biao";
                            $('#' + id)[0].innerHTML = data;
                            break;
                        }
                        case 3: {

                        }
                    }
                }
            });
        }
    </script>

    <script type="text/javascript">
        /**右上角退出系统**/
        function logout() {
            if (confirm("您确定要退出本系统吗？")) {
                window.location.href = "login.jsp";//跳转回登陆界面
            }
        }

        /**获得当前日期**/
        function getDate01() {
            var time = new Date();
            var myYear = time.getFullYear();
            var myMonth = time.getMonth() + 1;
            var myDay = time.getDate();
            if (myMonth < 10) {
                myMonth = "0" + myMonth;
            }
            document.getElementById("yue_fen").innerHTML = myYear + "." + myMonth;
            document.getElementById("day_day").innerHTML = myYear + "." + myMonth + "." + myDay;
        }
    </script>

    <style>
        ul, ol, li, dl, dt, dd {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }

        h1, h2, h3, form, input, iframe, span {
            margin: 0;
            padding: 0;
        }

        a {
            color: #7FB0C8;
        }

        a:link {
            color: #7FB0C8;
            TEXT-DECORATION: none;
        }

        a:visited {
            color: #7FB0C8;
            TEXT-DECORATION: none;
        }

        a:hover {
            color: #fff;
            TEXT-DECORATION: none;
        }

        .white {
            color: #fff;
        }

        .white a:link {
            color: #fff;
            TEXT-DECORATION: none;
        }

        .white a:visited {
            color: #fff;
            TEXT-DECORATION: none;
        }

        .white a:hover {
            color: #73E1F5;
            TEXT-DECORATION: none;
        }

        /* 树形菜单开始 */
        .close {
            float: right;
            clear: right;
            font-size: 12px;
            font-weight: normal;
            cursor: pointer;
            padding-right: 10px;
        }

        .title {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
            width: 290px;
        }

        .menu {
            width: 300px;
            height: 600px;
            overflow-y: auto;
            overflow-x: hidden;
            margin-bottom: 10px;
        }

        .menu {
            SCROLLBAR-FACE-COLOR: #002537;
            SCROLLBAR-HIGHLIGHT-COLOR: #002537;
            SCROLLBAR-SHADOW-COLOR: #0E6893;
            SCROLLBAR-3DLIGHT-COLOR: #0E6893;
            SCROLLBAR-ARROW-COLOR: #fff;
            SCROLLBAR-TRACK-COLOR: #053d58;
            SCROLLBAR-DARKSHADOW-COLOR: #002537;
            SCROLLBAR-BASE-COLOR: #01152a;
        }

        .l1 {
            font-size: 13px;
            padding: 5px 0 0 30px;
            height: 20px;
            margin-bottom: 5px;
            cursor: pointer;
        }

        .slist {
            margin: 0 0 5px 0;
            display: none;
        }

        /*.l2 { padding:0 0 0 35px; font-size:13px;}*/
        .l2 {
            padding: 6px 0 0 45px;
            font-size: 13px;
            width: 230px;
            height: 21px;
            display: block;
            color: #276989;
            cursor: pointer;
        }

        /*.currentl2 a,.l2 a:hover {  color:#000;}*/
        .sslist {
            width: 235px;
            overflow: hidden;
            margin: 0 0 5px 35px;
            display: none;
        }

        .l3 {
            padding: 6px 0 0 25px;
            width: 230px;
            height: 20px;
            display: block;
            color: #7FB0C8;
            cursor: pointer;
        }
    </style>

    <style>
        .alt td {
            background: black !important;
        }
    </style>

</head>
<body onload="getDate01()">
<div id="top">
    <div id="top_logo">
        <img alt="logo" src="images/common/logo.jpg" width="274" height="49" style="vertical-align:middle;">
    </div>
    <div id="top_links">
        <div id="top_op">
            <ul>
                <li>
                    <img alt="当前用户" src="images/common/user.jpg">：
                    <span><span>
                        <%
                            Cookie cookie = null;
                            Cookie[] cookies = null;
                            // 获取cookies的数据,是一个数组
                            cookies = request.getCookies();
                            if (cookies != null) {
                                for (int i = 0; i < cookies.length; i++) {//从cookie中获取当前已登陆用户
                                    cookie = cookies[i];
                                    if (cookie.getName().equals("username") && (cookie.getValue() != null)) {
                                        out.print(cookie.getValue());
                                        break;
                                    }
                                }
                            }
                        %>
                    </span></span>
                </li>
                <li>
                    <img alt="事务月份" src="images/common/month.jpg">：
                    <span id="yue_fen"></span>
                </li>
                <li>
                    <img alt="今天是" src="images/common/date.jpg">：
                    <span id="day_day"></span>
                </li>
            </ul>
        </div>
        <div id="top_close">
            <a href="javascript:void(0);" onclick="logout();" target="_parent">
                <img alt="退出系统" title="退出系统" src="images/common/close.jpg"
                     style="position: relative; top: 10px; left: 25px;">
            </a>
        </div>
    </div>
</div>
<!-- side menu start -->
<div id="side">
    <div id="left_menu">
        <ul id="TabPage2" style="height:200px; margin-top:50px;">
            <li id="left_tab1" class="selected" onClick="javascript:switchTab('TabPage2','left_tab1');" title="数据库">
                <a href="#"><img alt="数据库" title="数据库" src="images/common/1_hover.jpg" width="33" height="31">数据</a>
            </li>
            <li id="left_tab2" onClick="javascript:switchTab('TabPage2','left_tab2');" title="连接管理">
                <a href="#"><img alt="连接管理" title="连接管理" src="images/common/2.jpg" width="33" height="31">管理</a>
            </li>
            <li id="left_tab3" title="新建连接">
                <a href="#"
                   onclick="javascript:window.open('createUrl.html','','height=600,width=900,top=50,left=250,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no')"><img
                        title="新建连接" src="images/common/3.jpg" width="33" height="31" alt="新建连接">新建</a>
            </li>
        </ul>


        <div id="nav_show" style="position:absolute; bottom:0px; padding:10px;">
            <a href="javascript:;" id="show_hide_btn">
                <img alt="显示/隐藏" title="显示/隐藏" src="images/common/nav_hide.png" width="35" height="35">
            </a>
        </div>
    </div>
    <div id="left_menu_cnt">
        <div id="nav_module">
            <img src="images/common/module_1.png" width="210" height="58"/>
        </div>

        <div id="nav_resource">
            <div><h1 class="title"><span class="close"></span></h1></div>
            <div class="menu">
                <%
                    int url_name_num = (int) request.getAttribute("url_name_num");
                    for (int i = 0, ge = 0; i < url_name_num; i++, ge++) {
                        String url_name = (String) request.getAttribute("url_name" + i);
                        out.println("<h1 class=\"l1\" onclick=\"ajaxS(0," + ge + ",-1,-1,this)\">" + url_name
                                + "</h1><div class=\"slist\" id=\"0" + ge + "-1-1\">   </div>");
                    }
                %>
                <h1 class="l1">↑单击进行链接</h1>

            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(function () {
        $('#TabPage2 li').click(function () {
            var index = $(this).index();
            $(this).find('img').attr('src', 'images/common/' + (index + 1) + '_hover.jpg');
            $(this).css({background: '#fff'});
            $('#nav_module').find('img').attr('src', 'images/common/module_' + (index + 1) + '.png');
            $('#TabPage2 li').each(function (i, ele) {
                if (i != index) {
                    $(ele).find('img').attr('src', 'images/common/' + (i + 1) + '.jpg');
                    $(ele).css({background: '#044599'});
                }
            });
            // 显示侧边栏
            switchSysBar(true);
        });

        // 显示隐藏侧边栏
        $("#show_hide_btn").click(function () {
            switchSysBar();
        });
    });

    /**隐藏或者显示侧边栏**/
    function switchSysBar(flag) {
        var side = $('#side');
        var left_menu_cnt = $('#left_menu_cnt');
        if (flag == true) {	// flag==true
            left_menu_cnt.show(500, 'linear');
            side.css({width: '370px'});
            $('#top_nav').css({width: '77%', left: '304px'});
            $('#main').css({left: '400px'});
        } else {
            if (left_menu_cnt.is(":visible")) {
                left_menu_cnt.hide(10, 'linear');
                side.css({width: '60px'});
                $('#top_nav').css({width: '100%', left: '60px', 'padding-left': '28px'});
                $('#main').css({left: '60px'});
                $("#show_hide_btn").find('img').attr('src', 'images/common/nav_show.png');
            } else {
                left_menu_cnt.show(500, 'linear');
                side.css({width: '370px'});
                $('#top_nav').css({width: '77%', left: '304px', 'padding-left': '0px'});
                $('#main').css({left: '400px'});
                $("#show_hide_btn").find('img').attr('src', 'images/common/nav_hide.png');
            }
        }
    }
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';color:#ffffff">
    <div id="main">
        <form id="submitForm" name="submitForm" action="" method="post">
            <div id="container">
                <div class="ui_content">
                    <div>
                        <div style="float: left; line-height:40px">

                            <input type="button" value="新建查询" class="ui_input_btn01" onclick="search();"/>
                            <input type="button" value="新增" class="ui_input_btn01" id="addBtn"/>
                            <input type="button" value="删除" class="ui_input_btn01" onclick="ajaxS(3,-1,-1,-1,this);"/>
                            <input type="button" value="编辑" class="ui_input_btn01" id="importBtn"/>

                        </div>
                    </div>
                </div>
                <div class="ui_content">
                    <div class="ui_tb">
                        <table id="biao" class="table" cellspacing="0" cellpadding="0" width="100%" align="center"
                               border="0">

                        </table>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
   
 