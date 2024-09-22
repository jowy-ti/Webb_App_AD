/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

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

@WebServlet(name = "testDB", urlPatterns = {"/testDB"})
public class TestDB extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection connection = null;
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            String query;
            PreparedStatement statement;
                
            query = "drop table image";
            statement = connection.prepareStatement(query);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.                      
            statement.executeUpdate();
                
            query = "drop table usuarios"; 
            statement = connection.prepareStatement(query);
            statement.executeUpdate();      

            // fill parameters for prepared statement            
            // create and fill table usuarios            
            query = "create table usuarios (id_usuario varchar (256) primary key, password varchar (256))";
            statement = connection.prepareStatement(query);                        
            statement.executeUpdate();

            query = "create table image (id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "title varchar (256) NOT NULL, description varchar (1024) NOT NULL, keywords "
                + "varchar (256) NOT NULL, author varchar (256) NOT NULL, creator varchar (256) NOT NULL, "
                + "capture_date varchar (10) NOT NULL, storage_date varchar (10) NOT NULL, filename varchar (512) NOT NULL, "
                + "primary key (id), foreign key (creator) references usuarios(id_usuario))";

            statement = connection.prepareStatement(query);
            statement.executeUpdate();
                
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } 
        response.sendRedirect("http://localhost:8080/practica2/login.jsp");
    }
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
