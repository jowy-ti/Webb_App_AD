/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
            
            return 0;                                 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
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
            
            return 0;
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    static public int delete_image(int id) {
         // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
            query = "DELETE FROM image WHERE id = ?";
            statement = connection.prepareStatement(query); 
            statement.setInt(1, id);
            statement.executeUpdate();
                               
            return 0;
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return -1;
            
        } finally {
            // Se termina la conexión con la DB
            ConnectionDB.disconnectDB(connection);
        }
    }
    
    // clase utilizada para saber cuando poner la coma en la sentencia de update
    static class Booleanpointer {
        boolean value;
        
        Booleanpointer(boolean val) {
            value = val;
        }
    }
    
    static private String fill_query(String query, String field, String substitute, Booleanpointer coma) {
        
        // En caso de que el atributo sea null o esté vación no se usará en el update
        if (substitute != null && !substitute.isEmpty()) {
            if (coma.value) query = query + ", ";
            query = query + field + "'" + substitute + "'";
            coma.value = true;
        }
        
        return query;
    }
    
    static public int update_image(String title, String descr, String key_words,
            String author, String cap_date, String filename, int id) {
         // Se crea una conexión con la DB y se comprueba que ha salido bien
        Connection connection = ConnectionDB.connectDB();

        try {
            
            String query;
            PreparedStatement statement;
            Booleanpointer coma = new Booleanpointer(false);
            
           
            query = "UPDATE image SET ";
            String qtitle = "title = ";
            String qdescr = "description = ";
            String qkeywords = "keywords = "; 
            String qauthor = "author = ";
            String qcapdate = "capture_date = ";
            String qfilename = "filename = ";
            
            //rellenamos query según los atributos sean null o no
            query = fill_query(query, qtitle, title, coma);
            query = fill_query(query, qdescr, descr, coma);
            query = fill_query(query, qkeywords, key_words, coma);
            query = fill_query(query, qauthor, author, coma);
            query = fill_query(query, qcapdate, cap_date, coma);
            query = fill_query(query, qfilename,filename, coma);
            
            query = query + " WHERE id = " + id;
            System.out.println(query);
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
                               
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
