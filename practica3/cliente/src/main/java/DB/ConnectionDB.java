/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author alumne
 */
public class ConnectionDB {
   
    static public Connection connectDB(){
        Connection connection = null;
        try {
            
            // Se registra driver de la base de datos
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Conectamos con la base de datos
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/pr2;user=pr2;password=pr2");
            return connection;
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getMessage());
            return null;
        } 
    }
    
    static public void disconnectDB(Connection connection) {
        try {
            if (connection != null) connection.close();
            
        } catch (SQLException e) {
                // connection close failed.
            System.err.println(e.getMessage());
        }
    }
}
