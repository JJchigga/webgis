package login;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

//excute select function in openlayerLogged.jsp

@WebServlet("/Ajax")

public class Ajax extends HttpServlet {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF8");
        request.setCharacterEncoding("gb2312");

        String username = request.getParameter("username");
        String DBpassword = null;
        String DBusername = null;
        String JSONstring = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/WebGIS", "postgres", "528491");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        String sql = "select * from userMsg where username=?";
        try {
            //statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            while(resultSet.next()){
                DBusername = resultSet.getString("username");
                DBpassword = resultSet.getString("userpwd");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            JSONstring = JsonExchange(DBusername,DBpassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        PrintWriter writer = response.getWriter();
        writer.write(JSONstring);
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected String JsonExchange(String str1,String str2) throws SQLException {
//        String result = "{"+"\""+"name"+"\""+":"+"\""+str1+"\""+","+"\""+
//                "\n"+"password"+"\""+":"+"\""+str2+"\""+"}";
        String result = "name: "+str1+"\npassword: "+str2;

        return result;
    }
}
