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
import DB.UpdateDB;
import jakarta.servlet.RequestDispatcher;
import java.io.File;

/**
 *
 * @author alumne
 */
@WebServlet(name = "eliminarImagen", urlPatterns = {"/eliminarImagen"})
public class EliminarImagen extends HttpServlet {
    
    
    int id;
    String filename;
    
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
            File file = new File(path+"/"+filename);
            boolean deleted = file.delete();
            if (deleted == false) {
                //redirigir a pantalla de error
                response.sendRedirect("error.jsp");
            }
            else {
                request.setAttribute("message", "Imagen eliminada correctamente");
                request.setAttribute("registarImagen", false);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/operacionExitosa.jsp");
                dispatcher.forward(request, response);
            }
            
        } catch(IOException e) {
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
            filename = request.getParameter("filename");
            
            request.setAttribute("filename", filename);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/eliminarImagen.jsp");
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
