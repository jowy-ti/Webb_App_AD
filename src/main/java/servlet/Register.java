/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

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
import DB.queryDB;
import DB.updateDB;
/**
 *
 * @author alumne
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection connection = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            // create a database connection
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            
            /*
            // SQL Commands to create the database can be found in file database.sql		 		  
            */                     
            String user = request.getParameter("id_usuario");
            String passw = request.getParameter("password");
            //if(user == null or passw == null) errors.errors(0, true);
            
            
            int res = queryDB.exists_user(user, passw);
           
            if (res != 0) /*errors.error(res, false)*/;
            else {
                res = updateDB.add_user(user, passw);
                
                if (res == 0) response.sendRedirect("http://localhost:8080/menu");   
                else /*errors.error(res, false)*/;
           }
            

        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

