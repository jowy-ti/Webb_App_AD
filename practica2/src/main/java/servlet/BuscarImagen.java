/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author alumne
 */

@WebServlet(name = "BuscarImagen", urlPatterns = {"/BuscarImagen"})
public class BuscarImagen extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) response.sendRedirect("/practica2/error_out.jsp");
            
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String keywords = request.getParameter("keywords");
            ArrayList<ArrayList<String>> images = QueryDB.search_image(title, author, keywords);
            
            if (images == null)  {
                response.sendRedirect("/practica2/buscarImagen.jsp");
                return;
            }//error
            else if (images.isEmpty()) {
                response.sendRedirect("/practica2/buscarImagen.jsp");
                return;
            }//error
            
            request.setAttribute("images", images);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/buscarImagen.jsp");
            dispatcher.forward(request, response);
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
