/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author alumne
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

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
        	String user = request.getParameter("id_usuario");
        	String passw = request.getParameter("password");
       	 
                // Se comprueba si se ha enviado un usuario o contraseña vacios
                if(user.isEmpty() || passw.isEmpty()) {
                    response.sendRedirect("error_out.jsp");
                    return;
                }
                
        	String urlstring = "http://localhost:8080/servidor/resources/jakartaee9/login";
        	HttpURLConnection connection = null;
        	URL url = new URL(urlstring);
        	connection = (HttpURLConnection) url.openConnection();
       	 
        	connection.setRequestMethod("POST");
        	connection.setDoOutput(true);
        	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        	// Enviar los datos
        	//String postData = "string1=" + user + "&string2=" + passw;
                String postData = "username="+java.net.URLEncoder.encode(user, "UTF-8")+"&password="
                        +java.net.URLEncoder.encode(passw, "UTF-8");
        	OutputStream os = connection.getOutputStream();
        	os.write(postData.getBytes());
        	//os.flush();
       	 
        	// Leer la respuesta (opcional)
        	int responseCode = connection.getResponseCode();
        	System.out.println("Statuscode"+responseCode);
       	 
        	if (responseCode == HttpURLConnection.HTTP_OK) {
           	 
            	HttpSession sesion = request.getSession(true);
            	sesion.setAttribute("user", user);
            	response.sendRedirect("menu.jsp");
        	}  
                else response.sendRedirect("error_out.jsp");
       	 
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
