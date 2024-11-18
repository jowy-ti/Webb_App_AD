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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author alumne
 */
@WebServlet(name = "BuscarImagen", urlPatterns = {"/BuscarImagen"})
public class BuscarImagen extends HttpServlet {
    
    private static final String begin_url = "http://localhost:8080/servidor/resources/jakartaee9/";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        HttpURLConnection connection = null;
        int id_int;
        String id, title, author, keywords, filename, date, creator, description, election;
        
    	try {
            HttpSession sesion = request.getSession(false);
            if (sesion.getAttribute("user") == null) {
                response.sendRedirect("error_out.jsp");
                return;
            }
        	
            id = request.getParameter("id");
            title = request.getParameter("title"); 
            author = request.getParameter("author");
            keywords = request.getParameter("keywords");
            date = request.getParameter("date");
            
            election = (String) sesion.getAttribute("election");
            
            String urlstring = determine_election(election, id, title, author, keywords, date);
            
            if (urlstring == null) {
                response.sendRedirect("error.jsp");
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
                    description = jsonObject.getString("description");
                    author = jsonObject.getString("author");
                    keywords = jsonObject.getString("keywords");
                    filename = jsonObject.getString("filename");
                    date = jsonObject.getString("date");
                    creator = jsonObject.getString("creator");
                    
                    Image aux = new Image(id_int, title, author, keywords, filename, date, creator, description);
                    images.add(aux);
                    
                    downloadImage(filename);
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
    
    private static void downloadImage(String filename) throws IOException {
        URL url = new URL(begin_url + "/getimage/" + filename);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "*/*");

        int responseCode = connection.getResponseCode();
        switch (responseCode) {
            case 200:
                try (InputStream inputStream = connection.getInputStream()) {
                    String path_image = "/var/webapp/uploads/client/" + filename;
                    Files.copy(inputStream, Paths.get(path_image));
                    System.out.println("Downloaded: " + filename);
                }   
                break;
            case 404:
                System.err.println("Error: 404");
                break;
            default:
                throw new IOException("Failed to download image. HTTP code: " + responseCode);
        }
    }
    
    static private String determine_election(String election, String id, String title, String author, String keywords, String date) {
        String urlstring;
        
        switch (election) {
            case "id":
                if (id == null || id.isEmpty()) return null;

                urlstring = begin_url + "searchID/" + id;
                break;
                    
            case "date":
                if (date == null || date.isEmpty()) return null;
                urlstring = begin_url + "searchCreationDate/" + date;
                break;
                
            case "keywords":
                if (keywords == null || keywords.isEmpty()) return null;
                urlstring = begin_url + "searchKeywords/" + keywords; 
                break;
                    
            case "title":
                if (title == null || title.isEmpty()) return null;
                urlstring = begin_url + "searchTitle/" + title;
                break;
                    
            case "author":
                if (author == null || author.isEmpty()) return null;
                urlstring = begin_url + "searchAuthor/" + author;
                break;

            case "title_author":
                if ((author == null || author.isEmpty()) || (title == null || title.isEmpty())) return null;
                urlstring = begin_url + "searchTitle_Author/" + title + "/" + author;
                break;
                    
            default:
                return null;
        }
        return urlstring;
    }
    
    static public class Image {
        private int id;
        private String title;
        private String description;
        private String date;
        private String author;
        private String keywords;
        private String filename;
        private String creator;

        public Image(int id, String title, String author, String keywords, String filename, String date, String creator, String description) {
            this.id = id;
            this.title = title;
            this.description = description;
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
        public String getDescription() {
            return description;
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
