/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DB.QueryDB;
import DB.UpdateDB;
//import Err.Errors;
/**
 *
 * @author alumne
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class Register_s extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try {
            
            String user = request.getParameter("id_usuario");
            String passw = request.getParameter("password");
            
            // Se comprueba si se ha enviado un usuario o contraseña vacios
            if (user.equals("") || passw.equals("")) {
                response.sendRedirect("error_out.jsp");
                return;
            }

            // Comprabamos si existen las cuentas y en caso de no existir se crean
            int res = QueryDB.exists_user(user, passw);

            if (res == -2) {
                res = UpdateDB.add_user(user, passw);
                
                if (res == 0) response.sendRedirect("login.jsp");

                else response.sendRedirect("error_out.jsp"); //error
            }
            else response.sendRedirect("error_out.jsp"); //error
            
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

