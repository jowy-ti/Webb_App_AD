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
import jakarta.json.JsonObject;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import jakarta.servlet.RequestDispatcher;
import java.io.StringReader;
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
        response.setContentType("text/html;charset=UTF-8");
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        HttpURLConnection connection = null;
        int id_int;
        String title, author, keywords, filename, date, creator;
        
    	try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) {
                response.sendRedirect("error_out.jsp");
                return;
            }
        	
            String id = request.getParameter("id");
            title = request.getParameter("title"); 
            author = request.getParameter("author");
            keywords = request.getParameter("keywords");
            date = request.getParameter("date");
            String begin_url = "http://localhost:8080/servidor/resources/jakartaee9/";
            String urlstring = null;
                   	 
            if (id != null) urlstring = begin_url + "searchID/" + id;
            else if (date != null) urlstring = begin_url + "searchCreationDate/" + date;
            else if (keywords != null) urlstring = begin_url + "searchKeywords/" + keywords;
            else if (title != null && author != null)  urlstring = begin_url + "searchTitle_Author/" + title + "/" + author;
            else if (title != null) urlstring = begin_url + "searchTitle/" + title;
            else if (author != null) urlstring = begin_url + "searchAuthor/" + author;
            
            if (urlstring == null) {
                response.sendRedirect("error_out.jsp");
                return;
            }
            
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
                
                JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse));
                JsonArray jsonArray = jsonReader.readArray();
                
                ArrayList<Image> images = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.getJsonObject(i);
                    
                    id_int = jsonObject.getInt("id");
                    title = jsonObject.getString("title");
                    author = jsonObject.getString("author");
                    keywords = jsonObject.getString("keywords");
                    filename = jsonObject.getString("filename");
                    date = jsonObject.getString("date");
                    creator = jsonObject.getString("creator");
                    
                    Image aux = new Image(id_int, title, author, keywords, filename, date, creator);
                    images.add(aux);
                }
                
                request.setAttribute("images", images);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/buscarImagen.jsp");
                dispatcher.forward(request, response);
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
    
    static public class Image {
        private int id;
        private String title;
        private String date;
        private String author;
        private String keywords;
        private String filename;
        private String creator;

        public Image(int id, String title, String author, String keywords, String filename, String date, String creator) {
            this.id = id;
            this.title = title;
            this.date = date;
            this.author = author;
            this.keywords = keywords;
            this.filename = filename;
            this.creator = creator;
        }
        public int getId() {
            return id;
        }
        public String getTitle() {
            return title;
        }
        public String getAuthor() {
            return author;
        }
        public String getKeywords() {
            return keywords;
        }
        public String getFilename() {
            return filename;
        }
        public String getDate() {
            return date;
        }
        public String getCreator() {
            return creator;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
