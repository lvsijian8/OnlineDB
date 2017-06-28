package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement stat;
    ResultSet rs = null;

    public void init() throws ServletException {// 初始化代码仅进行一次的数据库连接操作
        mysqlUser.User();
        try {
            Class.forName(mysqlUser.driver);
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
            //con=DriverManager.getConnection("jdbc:mysql://lvsijian.cn:3306/onlinedb?" +"user=root&password=122344566788.Lv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (mysqlUser.checkConnection(con) || con == null || con.isClosed())
                con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        String password = new String(request.getParameter("password").getBytes("ISO8859-1"), "UTF-8");
        boolean mark = false;
        String user_password11 = "";//从user表里查询当前用户的列名为"user_password"的值
        String sql = "SELECT user_password FROM user WHERE user_name='" + username + "';";
        try {
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                user_password11 = "" + rs.getString("user_password");    //把查询到的"user_password"值给user_password11
            }
            if (user_password11.equals(password)) {//数据库中存储的密码与用户输入密码一致
                mark = true;
            }
            if (username.equals("") || password.equals(""))//密码为空
                mark = false;
            if (mark) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                String username1 = "登录成功";
                String docType = "<!DOCTYPE html> \n";
                out.println(docType +
                        "<html>\n" +
                        "<head><username>" + "</username></head>\n" +
                        "<body bgcolor=\"#f0f0f0\">\n" +
                        "<br><br><h1 align=\"center\">" + username1 + "</h1>\n" +
                        "<br><div  style=\"text-align:center;\"><a href=\"indexLoad\">点击返回首页</a></div>" +
                        "</body></html>");
                int user_nameId = 0;
                String sql_user_nameId = "SELECT user_id FROM user WHERE user_name='" + username + "'";//查询sql_user_nameId
                stat = con.createStatement();
                rs = stat.executeQuery(sql_user_nameId);
                while (rs.next()) {
                    user_nameId = rs.getInt("user_id");
                }
                Cookie usernameCo = new Cookie("username", username);
                Cookie user_nameIdCo = new Cookie("user_nameId", (user_nameId + ""));
                if (request.getParameter("autologin") != null) {// 记住密码
                    usernameCo.setMaxAge(60 * 60 * 24 * 15);
                    user_nameIdCo.setMaxAge(60 * 60 * 24 * 15);
                }
                response.addCookie(usernameCo);
                response.addCookie(user_nameIdCo);//保存用户名以及ID
            } else {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                String username1 = "密码错误，请重新登陆";
                String docType = "<!DOCTYPE html> \n";
                out.println(docType +
                        "<html>\n" +
                        "<head><username>" + "</username></head>\n" +
                        "<body bgcolor=\"#f0f0f0\">\n" +
                        "<br><br><h1 align=\"center\">" + username1 + "</h1>\n" +
                        "<br><div  style=\"text-align:center;\"><a href=\"login.jsp\">点击返回登录页面</a></div>" +
                        "</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {// 终止化代码,关闭数据库连接
        mysqlUser.over(rs,stat,con);
    }
}
