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
import java.time.LocalDate;

/**
 *
 * @author alumne
 */
public class UpdateDB {
    
    static public int add_user(String id_usuario, String password){
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
            
            // Actualizamos la DB con una nueva cuenta
            query = "insert into usuarios values(?,?)";
            statement = connection.prepareStatement(query);    
            statement.setString(1, id_usuario);
            statement.setString(2, password);
            statement.executeUpdate();
                                 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
        return 0;
    }
    
    static public int add_image(String title, String descr, String key_words,
            String author, String creator, String cap_date, String filename){
        
        // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
            //Obtenemos la data de almacenamiento, que es la actual
            LocalDate date = LocalDate.now();
            String storage_date = date.toString();
            
            // Actualizamos la DB con una nueva imagen
            query = "insert into image (TITLE,DESCRIPTION,KEYWORDS,AUTHOR,CREATOR,CAPTURE_DATE,STORAGE_DATE,FILENAME)"
                    + "values (?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);    
            statement.setString(1, title);
            statement.setString(2, descr);
            statement.setString(3, key_words);
            statement.setString(4, author);
            statement.setString(5, creator);
            statement.setString(6, cap_date);
            statement.setString(7, storage_date);
            statement.setString(8, filename);
            statement.executeUpdate();
                                 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
        return 0;
    }
    
    static public int delete_image(String filename, String title, String keywords) {
         // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
            query = "DELETE FROM image WHERE title = ? && filename = ?";
            statement = connection.prepareStatement(query); 
            statement.setString(1, title);
            statement.setString(2, filename);
            statement.executeUpdate();
                               
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
        return 0;
    }
    
    static public int update_image(String title, String descr, String key_words,
            String author, String cap_date, String filename, int id) {
         // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
           
            query = "UPDATE imagenes SET title = ?, description = ?, keywords = ?, author = ?, capture_date = ?, filename = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, descr);
            statement.setString(3, key_words);
            statement.setString(4, author);
            statement.setString(5, cap_date);
            statement.setString(6, filename);
            statement.executeUpdate();
                               
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
        return 0;
    }
}
