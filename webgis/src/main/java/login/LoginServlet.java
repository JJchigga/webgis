package login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        PrintWriter writer = response.getWriter();

        response.setContentType("text/html;charset=UTF8");
        request.setCharacterEncoding("gb2312");

        // 访问登录页面之前所访问的页面，可通过这个值跳转至之前的页面
        String returnUri = request.getParameter("return_uri");
        RequestDispatcher rd = null;

        //user's message from client
        String Cusername = request.getParameter("username");
        String Cpassword = request.getParameter("password");

       // System.out.println("OK");

        // 连接到 PostgreSQL 数据库
        String url = "jdbc:postgresql://localhost:5432/login";
        //user's message from database
        String DBusername = "null";
        String DBpassword = "null";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, "postgres", "123456");
            //connection =
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }



        //System.out.println(conn);

        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Cusername);
            preparedStatement.setString(2, Cpassword);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while (resultSet.next()) {
                DBusername = resultSet.getString("username");
                DBpassword = resultSet.getString("password");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (DBpassword.equals(Cpassword) && DBusername.equals(Cusername) && Cusername.equals("lwy")) {
            writer.write("1");
            writer.close();
            //将登录状态保存在session中
            request.getSession().setAttribute("flag","login_success");
//            //判断登陆之前的上一个页面是否存在
//            if(returnUri != null){
//                //存在则跳转到登陆之前的界面
//                rd = request.getRequestDispatcher(returnUri);
//                rd.forward(request,response);
//            }else{
//                //不存在则跳转首页
//                rd = request.getRequestDispatcher("/index.html");
//                rd.forward(request,response);
//            }
        }
        else if (DBpassword.equals(Cpassword) && DBusername.equals(Cusername) && Cusername.equals("zyy")) {
            writer.write("2");
            writer.close();
            //将登录状态保存在session中
            request.getSession().setAttribute("flag","login_success");
            }
        else if (DBpassword.equals(Cpassword) && DBusername.equals(Cusername) && Cusername.equals("wwj")) {
            writer.write("3");
            writer.close();
            //将登录状态保存在session中
            request.getSession().setAttribute("flag","login_success");
        }
        else if (DBpassword.equals(Cpassword) && DBusername.equals(Cusername)) {
            writer.write("10");
            writer.close();
            //将登录状态保存在session中
            request.getSession().setAttribute("flag","login_success");
        }
        else if (DBpassword.equals("null") || DBusername.equals("null")) {


            request.getSession().setAttribute("flag", "login_error");
            request.setAttribute("msg", "用户名或密码错误");
            // 失败后跳转到登录界面
            rd = request.getRequestDispatcher("/login.html");
            rd.forward(request, response);

            writer.write("0");
            writer.close();
        }
        //response.getWriter().write("hhh");

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        //response.getWriter().write("hhh");
    }
}
