package OnlineDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/indexLoad")
public class indexLoad extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement stat;
    ResultSet rs = null;
    int url_num = 0;//当前保存链接数
    int urls_num = 55;//单用户最大链接数
    int dbs_num = 105;//单链接中最大数据库数量
    int tables_num = 105;//单数据库中最大表数量
    int ColumnNames_num = 55;//单表中可显示最大列数
    Connection condbs[] = new Connection[urls_num];
    String url_urls[] = new String[urls_num];
    String url_names[] = new String[urls_num];
    String url_psws[] = new String[urls_num];
    String url_db_name[][] = new String[urls_num][dbs_num];
    String url_table_name[][][] = new String[urls_num][dbs_num][tables_num];

    public void init() throws ServletException {// 初始化代码仅进行一次的数据库连接操作
        mysqlUser.User();
        try {
            Class.forName(mysqlUser.driver);
            //con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (mysqlUser.checkConnection(con) || con == null || con.isClosed())
                con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (request.getParameter("ceng") != null) {//点击事件
            String dataString = "";
            try {
                String ceng = new String(request.getParameter("ceng").getBytes("ISO8859-1"), "UTF-8");
                String x = new String(request.getParameter("x").getBytes("ISO8859-1"), "UTF-8");
                String y = new String(request.getParameter("y").getBytes("ISO8859-1"), "UTF-8");
                String z = new String(request.getParameter("z").getBytes("ISO8859-1"), "UTF-8");
                int xint = Integer.parseInt(x);
                int yint = Integer.parseInt(y);
                int zint = Integer.parseInt(z);
                switch (ceng) {
                    case "0": {//点击链接,输出该链接中的数据库名
                        String sqldb = "SELECT SCHEMA_NAME FROM information_schema.SCHEMATA";
                        Statement statdb;
                        ResultSet rsdb;
                        if (condbs[xint] == null || condbs[xint].isClosed())
                            condbs[xint] = DriverManager.getConnection((url_urls[xint]), url_names[xint], url_psws[xint]);
                        statdb = condbs[xint].createStatement();
                        rsdb = statdb.executeQuery(sqldb);
                        int tmp = 0;
                        dataString = "";
                        while (rsdb.next()) {
                            url_db_name[xint][tmp] = rsdb.getString("SCHEMA_NAME");
                            dataString += "<h2 class=\"l2\" onclick=\"ajaxS(1," + xint + "," + tmp + ",-1,this)\">" + url_db_name[xint][tmp] + "</h2><ul class=\"sslist\" id=\"1" + xint + tmp + "-1\"> </ul>";
                            tmp++;
                        }
                        for (; tmp < dbs_num; tmp++)
                            url_db_name[xint][tmp] = "";
                        rsdb.close();
                        statdb.close();
                        break;
                    }
                    case "1": {//点击数据库,输出该数据库中的表名
                        String sqltable = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '" + url_db_name[xint][yint] + "';";
                        Statement stattable;
                        ResultSet rstable;
                        if (condbs[xint] == null || condbs[xint].isClosed())
                            condbs[xint] = DriverManager.getConnection((url_urls[xint] + url_db_name[xint][yint]), url_names[xint], url_psws[xint]);
                        stattable = condbs[xint].createStatement();
                        String sql_use_db = "use " + url_db_name[xint][yint] + ";";
                        stattable.executeQuery(sql_use_db);
                        rstable = stattable.executeQuery(sqltable);
                        int tmp = 0;
                        dataString = "";
                        while (rstable.next()) {
                            url_table_name[xint][yint][tmp] = rstable.getString("TABLE_NAME");
                            dataString += "<li class=\"l3\" onclick=\"ajaxS(2," + xint + "," + yint + "," + tmp + ",this)\">·"
                                    + url_table_name[xint][yint][tmp] + "</li>";
                            tmp++;
                        }
                        for (; tmp < tables_num; tmp++)
                            url_table_name[xint][yint][tmp] = "";
                        rstable.close();
                        stattable.close();
                        break;
                    }
                    case "2": {//点击表名,在右侧输出该表
                        int ColumnName_num = 0;
                        String ColumnNames[] = new String[ColumnNames_num];
                        String sqlbiao = "select COLUMN_NAME from information_schema.COLUMNS where table_name = '" + url_table_name[xint][yint][zint] + "' and table_schema = '" + url_db_name[xint][yint] + "';";
                        Statement statbiao;
                        ResultSet rsbiao;
                        if (condbs[xint] == null || condbs[xint].isClosed())
                            condbs[xint] = DriverManager.getConnection((url_urls[xint] + url_db_name[xint][yint]), url_names[xint], url_psws[xint]);
                        statbiao = condbs[xint].createStatement();
                        String sql_use_db = "use " + url_db_name[xint][yint] + ";";
                        statbiao.executeQuery(sql_use_db);
                        rsbiao = statbiao.executeQuery(sqlbiao);
                        dataString = "<tr><th width=\"30\"><input type=\"checkbox\" id=\"all\" onclick=\"selectOrClearAllCheckbox(this);\"/></th>";
                        while (rsbiao.next()) {
                            dataString += "<th>" + (ColumnNames[ColumnName_num++] = rsbiao.getString("COLUMN_NAME")) + "</th>";
                        }
                        String sqlbiao_nei = " SELECT * FROM " + url_table_name[xint][yint][zint] + ";";
                        statbiao = condbs[xint].createStatement();
                        rsbiao = statbiao.executeQuery(sqlbiao_nei);
                        for (int tmp = 0; rsbiao.next(); tmp++) {
                            dataString += "<tr><td><input type=\"checkbox\" name=\"BiaoCheck\" value=\"" + tmp + "\" class=\"acb\"/></td>";
                            for (int i = 0; i < ColumnName_num; i++) {
                                dataString += "<td>" + rsbiao.getString(ColumnNames[i]) + "</td>";
                            }
                            dataString += "</tr>";
                        }
                        rsbiao.close();
                        statbiao.close();
                        break;
                    }
                    case "3": {


                        break;
                    }
                }
            } catch (Exception e) {
                dataString = "<h2 class=\"l2\" >连接失败,请重新链接或确认链接信息</h2>";
                e.printStackTrace();
            }
            response.setContentType("text/html;charset=utf-8");//打印至HTML页面
            PrintWriter out = response.getWriter();
            out.print(dataString);
            out.close();
        } else {//页面加载事件
            Cookie cookie = null;
            Cookie[] cookies = null;
            // 获取cookies的数据,是一个数组
            String user_nameId = "";
            cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    cookie = cookies[i];
                    if (cookie.getName().equals("user_nameId") && (cookie.getValue() != null)) {// 读取当前用户
                        user_nameId = cookie.getValue();
                        break;
                    }
                }
            }
            try {
                for (int i = 0; i < url_num; i++) {//页面加载前,关闭所有数据库链接
                    if (condbs[i] != null) {
                        if (!condbs[i].isClosed()) {
                            condbs[i].close();
                            condbs[i] = null;
                        }
                    }
                }
                String sql_user_url = "SELECT url.url_name,url.url,url.url_user,url.url_password FROM user_url,url WHERE user_url.url_id=url.url_id&&user_url.user_id="
                        + user_nameId + ";";
                stat = con.createStatement();
                rs = stat.executeQuery(sql_user_url);// 查询当前用户下的url
                int i = 0;
                while (rs.next()) {
                    request.setAttribute("url_name" + (i), rs.getString("url_name"));
                    url_urls[i] = "jdbc:mysql://" + rs.getString("url") + "/";
                    url_names[i] = rs.getString("url_user");
                    url_psws[i] = rs.getString("url_password");
                    i++;
                }
                url_num = i;
                for (; i < urls_num; i++) {
                    url_urls[i] = "";
                    url_names[i] = "";
                    url_psws[i] = "";
                }
                request.setAttribute("url_name_num", url_num);
                request.getRequestDispatcher("index1.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }

    public void destroy() {// 终止化代码,关闭数据库连接
        try {
            for (int i = 0; i < url_num; i++) {
                if (condbs[i] != null) {
                    if (!condbs[i].isClosed()) {
                        condbs[i].close();
                        condbs[i] = null;
                    }
                }
            }
            mysqlUser.over(rs,stat,con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}