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
import java.io.IOException;
import java.net.HttpURLConnection;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alumne
 */

@WebServlet(name = "RegisterImage", urlPatterns = {"/registrarImagen"})
@MultipartConfig
public class RegisterImage extends HttpServlet {

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
                
                String title = request.getParameter("title");
        	String description = request.getParameter("description");
                String keywords = request.getParameter("keywords");
                String author = request.getParameter("author");
                String creator = sesion.getAttribute("user").toString();
                String capDate = request.getParameter("capture_date");
                
                //No dejamos que ningun parametro sea null
                if (title.isEmpty() || description.isEmpty() || keywords.isEmpty() ||
                    author.isEmpty() || creator.isEmpty() || capDate.isEmpty()) {
                    response.sendRedirect("error.jsp");
                    return;
                }
                
                final Part fileP = request.getPart("file");
                final String filename = fileP.getSubmittedFileName();

                if (filename.equals("")) {
                    response.sendRedirect("error.jsp");
                    return;
                }
                
                
                final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
                StreamDataBodyPart filePart = new StreamDataBodyPart("file", fileP.getInputStream());
                FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
                final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart
                        .field("title", title, MediaType.TEXT_PLAIN_TYPE)
                        .field("description", description, MediaType.TEXT_PLAIN_TYPE)
                        .field("keywords", keywords, MediaType.TEXT_PLAIN_TYPE)
                        .field("author", author, MediaType.TEXT_PLAIN_TYPE)
                        .field("creator", creator, MediaType.TEXT_PLAIN_TYPE)
                        .field("capture", capDate, MediaType.TEXT_PLAIN_TYPE)
                        .field("filename", filename, MediaType.TEXT_PLAIN_TYPE)
                        .bodyPart(filePart);

                final WebTarget target = client.target("http://localhost:8080/servidor/resources/jakartaee9/register");
                final Response resp = target.request().post(Entity.entity(multipart, multipart.getMediaType()));
                int status = resp.getStatus();

                formDataMultiPart.close();
                multipart.close();
                
       	 
        	if (status == HttpURLConnection.HTTP_OK) 
                    response.sendRedirect("operacionExitosa.jsp");
                else response.sendRedirect("error.jsp");
       	 
    	} catch (IOException e) {
        	System.err.println(e.getMessage());
    	}   	 
    }

}
