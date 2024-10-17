/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import DB.QueryDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import DB.UpdateDB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author alumne
 */
@WebServlet(name = "modificarImagen", urlPatterns = {"/modificarImagen"})
public class ModificarImagen extends HttpServlet {
    
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
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) response.sendRedirect("error_out.jsp");
        
            //Estos son los nuevos valores, si es null no se actualiza el valor antiguo
            String title = request.getParameter("title");
            String descr = request.getParameter("description");
            String key_words = request.getParameter("keywords");
            String author = request.getParameter("author");
            String cap_date = request.getParameter("capture_date");
            
            if (id < 0) response.sendRedirect("error.jsp");
            else {
                UpdateDB.update_image(title, descr, key_words, author, cap_date, id);
                
            }
            
            // Create path components to save the file
            final String path = "/var/webapp/uploads";
            final Part filePart = request.getPart("file");
            final String filename = getFileName(filePart);
            
            //Si entramos en el if significa que si hay un cambio de imagen
            if (!filename.equals("")) {
                UpdateDB.delete_image(id); //Se elimina la imagen anterior
                //Comprovamos que no exista una imagen con el mismo nombre
                //Se debería enviar a página de error
                if (QueryDB.exists_image(filename) == 0) {
                    response.sendRedirect("error.jsp");
                    return;
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
                
                String creator = sesion.getAttribute("user").toString();
                // Se añade los datos de la imagen a la BD
                UpdateDB.add_image(title, descr, key_words, author, creator, cap_date, filename);
                request.setAttribute("message", "Imagen modificada correctamente");
                request.setAttribute("registarImagen", false);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/operacionExitosa.jsp");
                dispatcher.forward(request, response);
            }
            
            
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

    private String getFileName(final Part part) {

        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
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
