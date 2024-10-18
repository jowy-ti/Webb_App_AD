/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testDB;

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
import DB.ConnectionDB;

/**
 *
 * @author alumne
 */

@WebServlet(name = "testDB", urlPatterns = {"/testDB"})
public class TestDB extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
                
            // Eliminamos las tabals de "image" y "usuarios"
            query = "drop table image";
            statement = connection.prepareStatement(query);
            statement.setQueryTimeout(30);  // set timeout to 30 sec.                      
            statement.executeUpdate();
                
            query = "drop table usuarios"; 
            statement = connection.prepareStatement(query);
            statement.executeUpdate();      
      
            // Creamos las tablas de "usuarios" y "image"
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
                
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
        response.sendRedirect("login.jsp");
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}