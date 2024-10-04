/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DB.QueryDB;
import Err.Errors;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 *
 * @author alumne
 */
@WebServlet(name = "RegistarImagen", urlPatterns = {"/registarImagen"})
@MultipartConfig
public class RegistarImagen extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

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
            //Id es asignado por nosotros (haria un id++ del último id usado en la base de datos
            //Creador == usuario
            String title = request.getParameter("title");
            String descr = request.getParameter("description");
            String key_words = request.getParameter("keywords");
            String author = request.getParameter("author");
            String cap_date = request.getParameter("capture_date");
            String filename = request.getParameter("filename");
            
            //Comprovamos si algun parametro es nulo
            if (title.equals("") || descr.equals("") ||
                    key_words.equals("") ||author.equals("") ||
                    cap_date.equals("") ||filename.equals("")) {
                //Tratamos error
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
