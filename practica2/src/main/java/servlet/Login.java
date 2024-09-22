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
import Err.errors;

/**
 *
 * @author alumne
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
          
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            
            // SQL Commands to create the database can be found in file database.sql		 		  
             
            
            String user = request.getParameter("id_usuario");
            String passw = request.getParameter("password");
            
            if(user.equals("") || passw.equals("")) {
                errors.login_error(0, true);
                response.sendRedirect("http://localhost:8080/practica2/login.jsp");
                return;
            }
            
            else {
                int res = queryDB.exists_user(user,passw);
                switch(res) {
                    case 0:
                    response.sendRedirect("http://localhost:8080/practica2/menu.jsp");
                   
                    default:
                    errors.login_error(res, false);
                    response.sendRedirect("http://localhost:8080/practica2/login.jsp");
                }
            }
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
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
