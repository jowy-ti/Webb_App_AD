/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import DB.QueryDB;
import DB.UpdateDB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author alumne
 */
@WebServlet(name = "eliminarImagen", urlPatterns = {"/eliminarImagen"})
public class eliminarImagen extends HttpServlet {
    
    
    int id;
    String fileName;
    
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
        
        try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
            
           
            UpdateDB.delete_image(id);
            String path = "/var/webapp/uploads";
            File file = new File(path+"/"+fileName);
            boolean deleted = file.delete();
            if (deleted == false) {
                //redirigir a pantalla de error
                 response.sendRedirect("error.jsp");
            }
            
        } catch(IOException e) {
            
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");

            id = Integer.parseInt(request.getParameter("id"));
            fileName = request.getParameter("filename");
        } catch (Exception e) {
            
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
