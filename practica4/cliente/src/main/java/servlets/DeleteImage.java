/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet    ;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.MultipartConfig;
import java.io.OutputStream;
import java.io.StringReader;

/**
 *
 * @author alumne
 */
@WebServlet(name = "DeleteImage", urlPatterns = {"/eliminarImagen"})
@MultipartConfig
public class DeleteImage extends HttpServlet {

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
        	HttpSession sesion = request.getSession(false);
                if (sesion.getAttribute("user") == null) {
                response.sendRedirect("error_out.jsp");
                return;
                }
                
                String creator = sesion.getAttribute("creator").toString();
                sesion.removeAttribute("creator");
                
                String id = sesion.getAttribute("id").toString();
                if (id != null) sesion.removeAttribute("id");
                
                //Eliminamos los metadatos de la imagen de la sesion
                /*
                sesion.removeAttribute("title");
                sesion.removeAttribute("description");
                sesion.removeAttribute("author");
                sesion.removeAttribute("keywords");
                sesion.removeAttribute("date");
                sesion.removeAttribute("filename");
                */
                
                if (creator == null || !creator.equals(sesion.getAttribute("user").toString())) {
                    
                    response.sendRedirect("error.jsp");
                    return;
                }
                
        	String urlstring = "http://localhost:8080/servidor/resources/jakartaee9/delete";
        	HttpURLConnection connection = null;
        	URL url = new URL(urlstring);
        	connection = (HttpURLConnection) url.openConnection();
        	connection.setRequestMethod("POST");
        	connection.setDoOutput(true);
        	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        	// Enviar los datos
                String postData = "id="+java.net.URLEncoder.encode(id, "UTF-8");
                        
        	OutputStream os = connection.getOutputStream();
        	os.write(postData.getBytes());
        	//os.flush();
       	 
        	// Leer la respuesta (opcional)
        	int responseCode = connection.getResponseCode();
        	System.out.println("Statuscode"+responseCode);
       	 
        	if (responseCode == HttpURLConnection.HTTP_OK) 
                    response.sendRedirect("operacionExitosa.jsp");
                else response.sendRedirect("error_out.jps");
       	 
    	} catch (IOException e) {
        	System.err.println(e.getMessage());
    	}  
    }

}
