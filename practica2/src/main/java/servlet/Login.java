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
import jakarta.servlet.http.HttpSession;
import DB.QueryDB;
import Err.Errors;

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
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try {
            
            String user = request.getParameter("id_usuario");
            String passw = request.getParameter("password");
            
            // Se comprueba si se ha enviado un usuario o contraseña vacios
            if(user.equals("") || passw.equals("")) {
                Errors.login_error(0, true);
                response.sendRedirect("/practica2/login.jsp");
                return;
            }
            
            // Se comprueba si existe la cuenta, si existe dejamos pasar
            int res = QueryDB.exists_user(user,passw);
            
            if (res == 0) {
<<<<<<< HEAD
                HttpSession sesion = request.getSession(true);
=======
                HttpSession sesion = request.getSession();
                sesion.setMaxInactiveInterval(10);
>>>>>>> c751829 (si)
                sesion.setAttribute("user", user);
                response.sendRedirect("/practica2/menu.jsp");
            }   
            else {    
                Errors.login_error(res, false);
                response.sendRedirect("/practica2/login.jsp");  
            }
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
