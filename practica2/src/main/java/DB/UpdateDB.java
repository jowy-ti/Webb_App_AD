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
            //Obtenemos el id a asignar
            query = "SELECT * FROM image WHERE image.id = ( SELECT MAX(image.id) FROM image)";
            int id = Integer.parseInt(query) + 1;
            String storage_date = "27/09/2024"; //ESTA MAL, VALOR DE PRUEBA
            
            // Actualizamos la DB con una nueva imagen
            query = "insert into images values(?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);    
            statement.setInt(1, id);
            statement.setString(2, title);
            statement.setString(3, descr);
            statement.setString(4, key_words);
            statement.setString(5, author);
            statement.setString(6, creator);
            statement.setString(7, cap_date);
            statement.setString(8, storage_date);
            statement.setString(9, filename);
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
