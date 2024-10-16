/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

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
            query = "select * from usuarios where id_usuario = '"+id_usuario+"' and password = '"+password+"'";
            statement = connection.prepareStatement(query);
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
    
    static public ArrayList<ArrayList<String>> search_image(String title, String author, String keywords) {
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();
        
        try {
            
            String query, querynull;
            PreparedStatement statement;
            
            String qtitle = "title LIKE '%"+title+"%'";
            String qauthor = "author LIKE '%"+author+"%'";
            String qkeywords = "keywords LIKE '%"+keywords+"%'";   
            
            query = "select * from image where ";
            querynull = "select * from image where ";
            boolean or = false;
            
            if (!title.isEmpty()) {
                query = query + qtitle;
                or = true;
            }
            if (!author.isEmpty()) {
                if (or) query = query + " OR ";
                query = query + qauthor;
                or = true;
            }
            if (!keywords.isEmpty()) {
                if (or) query = query + " OR ";
                query = query + qkeywords;
            }
            
            if (query.equals(querynull)) query = query + "'1' = '1'";
            
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            ArrayList<ArrayList<String>> images = new ArrayList<>();
            ArrayList<String> info;

            while (rs.next()) {
                info = new ArrayList<>();
                info.add(rs.getString("title"));
                info.add(rs.getString("description"));
                info.add(rs.getString("author"));
                info.add(rs.getString("filename"));
                info.add(rs.getString("id"));
                info.add(rs.getString("creator"));
                info.add(rs.getString("keywords"));
                images.add(info);
            }

            return images;
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    static public int exists_image(String filename){
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();
        
        try {
            
            String query;
            PreparedStatement statement;
            query = "select filename from image where filename = '"+filename+"'";
            statement = connection.prepareStatement(query);
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
    
}
