package login;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import netscape.javascript.JSObject;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

//excute register function in openlayerLogged.jsp

@WebServlet("/Register")

public class Register extends HttpServlet {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF8");
        request.setCharacterEncoding("gb2312");

        String RstName = request.getParameter("username");
        String RstPwd = request.getParameter("password");

        try {
            Class.forName("org.postgresql.Driver");
            connection = (Connection) DriverManager.getConnection("jdbc:postgresql://localhost:5432/login", "postgres", "123456");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        if(RstName.equals("")||RstPwd.equals("")){
            PrintWriter writer = response.getWriter();
            writer.write("0");
        }
        else{
            String sql = "insert into users values(?,?)";
            try {
                //statement = connection.createStatement();
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,RstName);
                preparedStatement.setString(2,RstPwd);
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
