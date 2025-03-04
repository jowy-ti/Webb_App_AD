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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author alumne
 */
@WebServlet(name = "RegisterUser", urlPatterns = {"/registerUser"})
public class RegisterUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    	try {
        	String user = request.getParameter("id_usuario");
        	String passw = request.getParameter("password");
                String confirmPass = request.getParameter("confirmPass");
                
                if (user.isEmpty() || passw.isEmpty() || confirmPass.isEmpty()) {
                    response.sendRedirect("error_out.jsp");
                    return;
                }
                
                if (!passw.equals(confirmPass)) {
                    response.sendRedirect("error_out.jsp");
                    return;
                }
                    
        	String urlstring = "http://localhost:8080/servidor/resources/jakartaee9/registerUser";
        	HttpURLConnection connection = null;
        	URL url = new URL(urlstring);
        	connection = (HttpURLConnection) url.openConnection();
       	 
        	connection.setRequestMethod("POST");
        	connection.setDoOutput(true);
        	connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        	// Enviar los datos
                String postData = "username="+java.net.URLEncoder.encode(user, "UTF-8")
                        +"&password="+java.net.URLEncoder.encode(passw, "UTF-8")
                        +"&confirmPass="+java.net.URLEncoder.encode(confirmPass, "UTF-8");
        	OutputStream os = connection.getOutputStream();
        	os.write(postData.getBytes());
        	//os.flush();
       	 
        	// Leer la respuesta (opcional)
        	int responseCode = connection.getResponseCode();
        	System.out.println("Statuscode"+responseCode);
       	 
        	if (responseCode == HttpURLConnection.HTTP_OK)
                    response.sendRedirect("login.jsp");
                else response.sendRedirect("error_out.jsp");
        	 
       	 
    	} catch (IOException e) {
        	System.err.println(e.getMessage());
    	}   
    }

}
