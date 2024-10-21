/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import DB.QueryDB;
import jakarta.ws.rs.core.Response;
//import Err.Errors;


/**
 *
 * @author alumne
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    /**
    * POST method to login in the application
    * @param username
    * @param password
    * @return
    *
    *@Path("login")
    *@POST
    *@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    *@Produces(MediaType.APPLICATION_JSON)
    **/
    public Response login(String username, String password)  {
        try {
            
            // Se comprueba si se ha enviado un usuario o contraseña vacios
            if(username.equals("") || password.equals("")) {
                return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("Invalid credentials: null")
                        .build();
            }
            
            // Se comprueba si existe la cuenta, si existe dejamos pasar
            int res = QueryDB.exists_user(username,password);
            
            if (res == 0) return Response.accepted().build();
            else return Response.status(Response.Status.NOT_ACCEPTABLE)
                        .entity("The user with credentials $username, $password does not exist")
                        .build(); //error
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }        
    }
    
}
