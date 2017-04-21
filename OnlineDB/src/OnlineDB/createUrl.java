package OnlineDB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/createUrl")
public class createUrl extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (mysqlUser.checkConnection(con) || con == null || con.isClosed())//检查链接是否正常
                con = DriverManager.getConnection(mysqlUser.url, mysqlUser.user, mysqlUser.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String url = new String(request.getParameter("url").getBytes("ISO8859-1"), "UTF-8");
            String url_user = new String(request.getParameter("url_user").getBytes("ISO8859-1"), "UTF-8");
            String url_password = new String(request.getParameter("url_password").getBytes("ISO8859-1"), "UTF-8");
            String url_name = new String(request.getParameter("url_name").getBytes("ISO8859-1"), "UTF-8");
            if (url_name.equals(""))
                url_name = "我的连接";
            String sql = "INSERT INTO url (url,url_user,url_password,url_name) VALUES('" + url + "','" + url_user + "','"
                    + url_password + "','" + url_name + "');";
            String textString = "创建成功";
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
            int url_idMax = -1;
            String stlUrlis = "SELECT url_id FROM url WHERE url='" + url + "'&&url_user='" + url_user
                    + "'&&url_password='" + url_password + "';";
            stat = con.createStatement();
            rs = stat.executeQuery(stlUrlis);// 判断当前url是否已存在
            while (rs.next()) {
                url_idMax = rs.getInt("url_id");
            }
            if (url_idMax == -1) {// 如果url不存在
                stat = con.createStatement();
                stat.executeUpdate(sql);// 插入新建的url
                String sqlMaxId = "SELECT MAX(url_id) FROM url";// 查询新建的url_id
                stat = con.createStatement();
                rs = stat.executeQuery(sqlMaxId);
                while (rs.next()) {
                    url_idMax = rs.getInt("MAX(url_id)");
                }
                String sql_user_url = "INSERT INTO user_url (user_id,url_id) VALUES(" + user_nameId + "," + url_idMax + ");";
                stat = con.createStatement();
                stat.executeUpdate(sql_user_url);// 插入新建的url_id和user_id的关联
            } else {// 如果url存在
                String sqlUser_url_id = "SELECT user_id FROM user_url WHERE url_id=" + url_idMax + ";";
                stat = con.createStatement();
                rs = stat.executeQuery(sqlUser_url_id);
                boolean mark = false;
                while (rs.next()) {
                    if (user_nameId.equals((rs.getInt("user_id") + ""))) {// 当前url当前用户曾经保存过
                        mark = true;
                    }
                }
                if (mark) {// 当前url当前用户曾经保存过
                    textString = "当前此url已存在.";
                } else {// 创建此url与用户的关联
                    String sql_user_url = "INSERT INTO user_url (user_id,url_id) VALUES(" + user_nameId + "," + url_idMax + ");";
                    stat = con.createStatement();
                    System.out.println(sql_user_url);
                    stat.executeUpdate(sql_user_url);// 插入新建的url_id和user_id的关联
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String docType = "<!DOCTYPE html> \n";
            out.println(docType + "<html>\n" + "<head><username>" + "</username></head>\n"
                    + "<body bgcolor=\"#f0f0f0\">\n" + "<br><br><h1 align=\"center\">" + textString + "</h1>\n"
                    + "<br><div  style=\"text-align:center;\"><a onClick=\"window.close()\" href=\"\">点击返回首页后刷新查看</a></div>"
                    + "</body></html>");
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    public void destroy() {// 终止化代码,关闭数据库连接
        mysqlUser.over(rs,stat,con);
    }
}
