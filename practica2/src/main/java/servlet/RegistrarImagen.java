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
import DB.UpdateDB;
import Err.Errors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jakarta.servlet.http.Part;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.*;

/**
 *
 * @author alumne
 */
@WebServlet(name = "RegistrarImagen", urlPatterns = {"/registrarImagen"})
@MultipartConfig
public class RegistrarImagen extends HttpServlet {
    
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
        
        HttpSession sesion = request.getSession(false);
        if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
    
        try {
            //Creador == usuario
            String title = request.getParameter("title");
            String descr = request.getParameter("description");
            String key_words = request.getParameter("keywords");
            String author = request.getParameter("author");
            String creator = sesion.getAttribute("user").toString();
            String cap_date = request.getParameter("capture_date");
            
            //Comprovamos si algun parametro es nulo
            if (title.equals("") || descr.equals("") ||
                    key_words.equals("") ||author.equals("") ||
                    cap_date.equals("")) {
                //Tratamos error
                response.sendRedirect("registrarImagen.jsp");
                return;
            }
             
            // Create path components to save the file
            final String path = "/var/webapp/uploads";
            final Part filePart = request.getPart("file");
            final String filename = getFileName(filePart);
            
            //Comprovamos que no exista una imagen con el mismo nombre
            //Se debería enviar a página de error
            if (QueryDB.exists_image(filename) == 0) {
                response.sendRedirect("error_out.jsp");
                return; //Paramos la ejecucion antes de añadir la imagen
            }

            OutputStream out = null;
            InputStream filecontent = null;

            try {
                out = new FileOutputStream(new File(path + File.separator
                        + filename));
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }


            } catch (FileNotFoundException fne) {

            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        
            // Añadimos el atributo filename a la sesión
            Filename_in_session(filename, sesion);

            UpdateDB.add_image(title, descr, key_words, author, creator, cap_date, filename);
            //todo ha ido bien
            response.sendRedirect("registrarImagen.jsp");
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } 
    }
    
    private String getFileName(final Part part) {

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
    private void Filename_in_session(String filename, HttpSession sesion) {
        
        String files_sesion = "session_images";
        ArrayList<String> filenames = (ArrayList<String>)sesion.getAttribute(files_sesion);
        if (filenames == null) filenames = new ArrayList<>();

        filenames.add(filename);
        sesion.setAttribute(files_sesion, filenames);
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
