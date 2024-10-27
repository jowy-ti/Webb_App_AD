/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        HttpURLConnection connection = null;
        
    	try {
            
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) {
                response.sendRedirect("error_out.jsp");
                return;
            }
        	
            String id = request.getParameter("id");
            String title = request.getParameter("title"); 
            String author = request.getParameter("author");
            String keywords = request.getParameter("keywords");
            String date = request.getParameter("date");
       	 
            String urlstring = "http://localhost:8080/servidor/resources/jakartaee9/searchID/" + id;

            URL url = new URL(urlstring);
            connection = (HttpURLConnection) url.openConnection();
       	 
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
       	 
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                
                InputStream input = connection.getInputStream();
                reader = new InputStreamReader(input);
                bufferedReader = new BufferedReader(reader);
                StringBuilder Response = new StringBuilder();
                String Line;
                
                while ((Line = bufferedReader.readLine()) != null) Response.append(Line);

                // Convertir el StringBuilder a String
                String jsonResponse = Response.toString();
                System.out.println(jsonResponse);
                
            }
       	 
    	} catch (IOException e) {
        	System.err.println(e.getMessage());
    	}  finally {
            // Cerrar el BufferedReader y el InputStreamReader
            if (bufferedReader != null) bufferedReader.close();

            if (reader != null) reader.close();
            
            if (connection != null) connection.disconnect(); // Cerrar la conexión
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
