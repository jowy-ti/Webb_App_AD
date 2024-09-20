/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author alumne
 */
public class queryDB

{
    
    static public int user_login(String id_usuario, String password){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            String query;
            PreparedStatement statement;


            query = "select * from usuarios where id_usuario IN ('"+id_usuario+"')";
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery(); 
            
            rs.next();
            if (!rs.getString("id_usuario").equals(id_usuario)) return -1;
            if (!rs.getString("password").equals(password)) return -2;
            
            return 0;
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return -3;
        }
    }

}
