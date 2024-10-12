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
import jakarta.servlet.RequestDispatcher;
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
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class modificarImagen extends HttpServlet {
    
    int id;
    
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
             //Estos son los nuevos valores, si es null no se actualiza el valor antiguo
            String title = request.getParameter("title");
            String descr = request.getParameter("description");
            String key_words = request.getParameter("keywords");
            String author = request.getParameter("author");
            String cap_date = request.getParameter("capture_date");
            
            if (id < 0) response.sendRedirect("error_out.jsp");
            else UpdateDB.update_image(title, descr, key_words, author, cap_date, id);
            response.sendRedirect("buscarImagen.jsp");
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");

            id = Integer.parseInt(request.getParameter("id"));
            String filename = request.getParameter("filename");
            
            request.setAttribute("filename", filename);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/modificarImagen.jsp");
            dispatcher.forward(request, response);
            
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
