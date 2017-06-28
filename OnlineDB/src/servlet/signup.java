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


@WebServlet("/signup")
public class signup extends HttpServlet {
    private static final long serialVersionUID = 1L;
    Connection con;
    Statement stat;
    ResultSet rs = null;

    public void init() throws ServletException {// 初始化代码仅进行一次的数据库连接操作
        mysqlUser.User();
        try {
            Class.forName(mysqlUser.driver);
            con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        String username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
        String password = new String(request.getParameter("password").getBytes("ISO8859-1"), "UTF-8");
        String email = new String(request.getParameter("email").getBytes("ISO8859-1"), "UTF-8");
        String phone = new String(request.getParameter("userEntity.tel").getBytes("ISO8859-1"), "UTF-8");
        boolean mark = false;
        String sql1 = "SELECT count(user_name) FROM user WHERE user_name='" + username + "';";
        String sql = "INSERT INTO user (user_name,user_password,phone,email) VALUES('" + username + "','" + password
                + "','" + phone + "','" + email + "');";
        try {
            stat = con.createStatement();
            rs = stat.executeQuery(sql1);
            while (rs.next()) {
                if (0 != rs.getInt("count(user_name)"))//查询当前注册用户名在数据库中的数量
                    mark = true;
            }
            if (username.equals("") || password.equals(""))
                mark = true;
            if (mark) {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                String username1 = "注册失败";
                String docType = "<!DOCTYPE html> \n";
                out.println(docType + "<html>\n" + "<head><username>" + "</username></head>\n"
                        + "<body bgcolor=\"#f0f0f0\">\n" + "<br><br><h1 align=\"center\">" + username1 + "</h1>\n"
                        + "<br><div  style=\"text-align:center;\"><a href=\"login.jsp\">用户名已存在,点击返回登陆.</a></div>"
                        + "</body></html>");
            } else {
                stat = con.createStatement();
                stat.executeUpdate(sql);
                int user_nameId = 0;
                String sql_user_nameId = "SELECT user_id FROM user WHERE user_name='" + username + "'";
                stat = con.createStatement();
                rs = stat.executeQuery(sql_user_nameId);//查询sql_user_nameId
                while (rs.next()) {
                    user_nameId = rs.getInt("user_id");
                }
                Cookie usernameCo = new Cookie("username", username);//保存用户名以及ID
                response.addCookie(usernameCo);
                Cookie user_nameIdCo = new Cookie("user_nameId", (user_nameId + ""));
                response.addCookie(user_nameIdCo);

                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                String username1 = "注册成功";
                String docType = "<!DOCTYPE html> \n";
                out.println(docType + "<html>\n" + "<head><username>" + "</username></head>\n"
                        + "<body bgcolor=\"#f0f0f0\">\n" + "<br><br><h1 align=\"center\">" + username1 + "</h1>\n"
                        + "<br><div  style=\"text-align:center;\"><a href=\"indexLoad\">注册成功，点击跳转首页。</a></div>"
                        + "</body></html>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {// 终止化代码,关闭数据库连接
        mysqlUser.over(rs,stat,con);
    }
}
