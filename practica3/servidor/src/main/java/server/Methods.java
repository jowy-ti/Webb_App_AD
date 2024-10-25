/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author alumne
 */
public class Methods {
   /**
* GET method to search images by id
* @param id
* @return
*/
@Path("searchID/{id}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response searchByID (@PathParam("id") int id) {
    
}
/**
* GET method to search images by title
* @param title
* @return
*/
@Path("searchTitle/{title}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response searchByTitle (@PathParam("title") String title) {
    
}
/**
* GET method to search images by creation date. Date format should be
* yyyy-mm-dd
* @param date
* @return
*/
@Path("searchCreationDate/{date}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response searchByCreationDate (@PathParam("date") String date) {
    
}
/**
* GET method to search images by author
* @param author
* @return
*/
@Path("searchAuthor/{author}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response searchByAuthor (@PathParam("author") String author) {
    
}
/**
* GET method to search images by keyword
* @param keywords
* @return
*/
@Path("searchKeywords/{keywords}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response searchByKeywords (@PathParam("keywords") String keywords) {
    
}
}
