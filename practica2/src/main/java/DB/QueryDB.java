/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DB.ConnectionDB;

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
    
    static public int search_image(String title, String author, String keywords) {
        
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
            
            if (title != null) query = query + qtitle;
            if (author != null) query = query + qauthor;
            if (keywords != null) query = query + qkeywords;
            if (query.equals(querynull)) query = query + "1 = 1";
            
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            return 0;
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
}
