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

@WebServlet(name = "BuscarImagen", urlPatterns = {"/BuscarImagen"})
public class BuscarImagen extends HttpServlet {
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("image/jpg");
        try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) response.sendRedirect("/practica2/error_out.jsp");
            
            String title = request.getParameter("title");
            String author = request.getParameter("author");
            String keywords = request.getParameter("keywords");
            
            ArrayList<String> filenames = QueryDB.search_image(title, author, keywords);
            
            if (filenames == null)  return;//error
            else if (filenames.isEmpty()) return;//error
            

            final String path = "/home/alumne/Imágenes/";
            File imageFile;
            FileInputStream fis;
            OutputStream os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            
            for (int i = 0; i < filenames.size(); ++i) {

                imageFile = new File(path+filenames.get(i));
                response.setContentLength((int) imageFile.length());      
                fis = new FileInputStream(imageFile);
                            
                while (fis.read(buffer) != -1) os.write(buffer);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
