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
import DB.QueryDB;
import DB.UpdateDB;
import Err.Errors;
/**
 *
 * @author alumne
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            String user = request.getParameter("id_usuario");
            String passw = request.getParameter("password");
            
            // Se comprueba si se ha enviado un usuario o contraseña vacios
            if (user.equals("") || passw.equals("")) {
                Errors.register_error(0, true);
                response.sendRedirect("register.jsp");
                return;
            }

            // Comprabamos si existen las cuentas y en caso de no exister se crean
            int res = QueryDB.exists_user(user, passw);

            if (res == -2) {
                res = UpdateDB.add_user(user, passw);
                
                if (res == 0) {
                    response.sendRedirect("login.jsp");
                    return;
                }   
                else Errors.register_error(res, false);
            }
            else Errors.register_error(res, false);
            
            response.sendRedirect("register.jsp");
            
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

