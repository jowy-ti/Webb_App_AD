/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import image.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alumne
 */
public class QueryDB {
    
    static public int exists_user(String id_usuario, String password){
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();
        
        try {
            
            String query;
            PreparedStatement statement;

            // Miramos si existe una cuenta con "id_usuario" y "password";
            //query = "select * from usuarios where id_usuario = '"+id_usuario+"' and password = '"+password+"'";
            query = "select * from usuarios where id_usuario = ? and password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, id_usuario);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery(); 
            
            if (rs.next()) return 0; //Usuario existe
            
            return -2; //Usuario no existe
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    static public ArrayList<Image> search_image(int int_param, String string_param, String name_param) {
        Connection connection = ConnectionDB.connectDB();
        
        try {
            
            PreparedStatement statement;
            String query;
            
            if (int_param >= 0) {
                query = "select * from image where "+name_param+" = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, int_param);
            }
            else {
                query = "select * from image where "+name_param+" LIKE ?";
                statement = connection.prepareStatement(query);
                statement.setString(1, "%" + string_param + "%");
            }
            
            ResultSet rs = statement.executeQuery();
            ArrayList<Image> images = ResultSet(rs);
            
            return images;
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    static public ArrayList<Image> search_image2(String param_title, String param_author) {
        Connection connection = ConnectionDB.connectDB();
        
        try {
            String query;
            PreparedStatement statement;
            
            String qtitle = "title LIKE ?";
            String qauthor = "author LIKE ?";
            
            query = "select * from image where ";
            boolean or = false;
            
            //query dinámica
            if (param_title != null) {
                query = query + qtitle;
                or = true;
            }
            if (param_author != null) {
                if (or) query = query + " OR ";
                query = query + qauthor;
            }
            
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + param_title + "%");
            statement.setString(2, "%" + param_author + "%");
            ResultSet rs = statement.executeQuery();
            
            ArrayList<Image> images = ResultSet(rs);
            
            return images;
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    static private ArrayList<Image> ResultSet(ResultSet rs) throws SQLException {
        int id;
        String title, capture_date, author, keywords, filename, creator, description;
        ArrayList<Image> images = new ArrayList<>();
        Image info;

        while (rs.next()) {
            id = rs.getInt("id");
            description = rs.getString("description");
            title = rs.getString("title");
            author = rs.getString("author");
            keywords = rs.getString("keywords");
            filename = rs.getString("filename");
            capture_date = rs.getString("capture_date");
            creator = rs.getString("creator");
                
            info = new Image(id, title, author, keywords, filename, capture_date, creator, description);
            images.add(info);
        }
        return images;
    }
    
    static public int exists_image(String filename){
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();
        
        try {
            
            String query;
            PreparedStatement statement;
            //query = "select filename from image where filename = '"+filename+"'";
            query = "select filename from image where filename = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, filename);
            ResultSet rs = statement.executeQuery(); 
            
            if (rs.next()) return 0; //Imagen con ese nombre existe
            
            return 1; 
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    static public String get_filename(int id){
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();
        
        try {
            
            String query;
            PreparedStatement statement;
            query = "select filename from image where id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) return rs.getString("filename"); 
            
            return ""; 
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
}
